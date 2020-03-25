import os
import networkx as nx
from Chromosome import Chromosome
from GA import GA


# read the network details
def readNet(fileName):
    network = nx.read_gml(fileName, label='id')
    problParam = {}
    matrix = []
    problParam['noNodes'] = network.number_of_nodes()
    problParam["noEdges"] = network.number_of_edges()

    for _ in network.nodes:
        matrix.append([0 for _ in network.nodes])

    degrees = [0 for _ in network.nodes]

    for i, j in network.edges:
        matrix[i - 1][j - 1] = matrix[j - 1][i - 1] = 1
        degrees[i - 1] = degrees[i - 1] + 1
        degrees[j - 1] = degrees[j - 1] + 1

    problParam['matrix'] = matrix
    problParam['degrees'] = degrees

    return problParam


def oldestParent(parent, nod):
    if parent[nod] != nod:
        parent[nod] = oldestParent(parent, parent[nod])
    return parent[nod]


def ancesterVector(c):
    parent = [i for i in range(0, c.problParam["noNodes"])]

    for i in range(0, c.problParam["noNodes"]):
        parent[oldestParent(parent, c.repres[i])] = oldestParent(parent, i)
    for i in range(0, c.problParam["noNodes"]):
        parent[i] = oldestParent(parent, i)

    return parent


def createComunities(anc, problParam):
    ap = [0 for _ in range(0, problParam["noNodes"])]
    normal = []
    index = 1
    for classLabel in anc:
        if ap[classLabel] == 0:
            ap[classLabel] = index
            index = index + 1
        normal.append(ap[classLabel])
    return normal, index - 1


def printResult(globalBest, problParam):
    anc = ancesterVector(globalBest)
    normal, index = createComunities(anc, problParam)

    print("Number of comnities for the best chromosome is: ", index)
    for i in range(0, problParam["noNodes"]):
        print(i, " ", normal[i])


def modularity(communities, param):
    noNodes = param['noNodes']
    matrix = param['matrix']
    degrees = param['degrees']
    noEdges = param['noEdges']
    M = 2 * noEdges
    Q = 0.0
    for i in range(0, noNodes):
        for j in range(0, noNodes):
            if (communities[i] == communities[j]):  # Kronecker function
                Q += (matrix[i][j] - degrees[i] * degrees[j] / M)
    return Q * 1 / M


def fcEval(c):
    communities = ancesterVector(c)
    a = modularity(communities, c.problParam)
    return a


def main():
    crtDir = os.getcwd()
    filePath = os.path.join(crtDir, 'input', 'karate.gml')
    network = readNet(filePath)

    gaParam = {"popSize": 500, "noGen": 100}

    problParam = {'function': fcEval,
                  'noNodes': network["noNodes"],
                  'noEdges': network["noEdges"],
                  'degrees': network["degrees"],
                  'matrix': network["matrix"]}

    globalBest = Chromosome(problParam)
    ga = GA(gaParam, problParam)
    ga.initialisation()
    ga.evaluation()
    contor = 1

    while contor <= gaParam['noGen']:
        ga.oneGeneration()
        #ga.oneGenerationElitism()
        #ga.oneGenerationSteadyState()

        bestChromo = ga.bestChromosome()
        aux, index = createComunities(ancesterVector(bestChromo), problParam)
        if bestChromo.fitness > globalBest.fitness:
            globalBest = bestChromo

        print("-"*200)
        print('gen: ', contor)
        print('Local  worst fit: ', ga.worstChromosome().fitness)
        print('Local  Best fit: ', bestChromo.fitness)
        print('Global Best fit: ', globalBest.fitness)
        print('Number of comunities: ', index)

        contor += 1

    printResult(globalBest, problParam)




main()
