
import java.util.LinkedList

class BTree<K: Comparable<K>> (val t: Int): Iterable<Node<K>>  {

    var root :Node<K>? = null

    fun insert(key: K) {

        if (root == null) root = Node()

        if (root!!.keys.size == 2 * t - 1) { // Размер корня равен максимуму(приходится разделять).

            val newroot: Node<K> = Node(false)

            newroot.children.add(root!!)
            newroot.divide(t, 0)

            root = newroot

            insertInNode(key, root!!)

        }

        else

            insertInNode(key, root!!)

    }

        private fun insertInNode(key: K, node: Node<K>)         {

        var x = findPosition(key, node) // x - position of new element.

        if (node.Leaf) node.keys.add(x, key) // Наш узел - лист.

        else { // Наш узел не лист - вставляем в детей.

            if (node.children[x].keys.size == 2 * t - 1) { // Ребенок переполнится.

                node.divide(t, x)

                if (key > node.keys[x]) x++

            }

            insertInNode(key, node.children[x])

        }
    }


    fun delete(key: K) {

        if (find(key) == false) return

        deleter(key, root!!)

        if (root!!.keys.size == 0) root = null

    }

        private fun deleter(key: K, node: Node<K>) {

        var x = findPosition(key, node)

        if (node.keys.size > x && node.keys[x] == key) {  //beginning of the first 'if' (ключ в нашем узле).

            when {

            node.Leaf -> node.keys.removeAt (x) // Наш узел - лист.

            node.children[x].keys.size > t - 1 -> { // Наш узел- не лист, но ближайший к удаляемому левый сын может отдать элемент.

                val LeftSon = NearestLeftSon(key, node)

                node.keys[x] = LeftSon.keys.last()

                deleter(LeftSon.keys.last(), node.children[x])

            }


            node.children[x + 1].keys.size > t - 1 -> { // Наш узел- не лист, но ближайший к удаляемому правый сын может отдать элемент.

                val RightSon = NearestRightSon(key, node)

                node.keys[x] = RightSon.keys.first()

                deleter(RightSon.keys.first(), node.children[x + 1])

            }

            else -> { //Наш узел - не лист и некому передавать элементы.

                node.merge(x)

                if (node.keys.isEmpty()) root = node.children[x]

                deleter(key, node.children[x])

            }
        }


        } else { //end of the first 'if' (else - ключ ниже)

            if (node.children[x].keys.size - 1 < t-1) { // beginning of the second 'if' (если в листе при удалении станет меньше чем t-1 элементов).

                when {

                    node.children[x] != node.children.last() && node.children[x + 1].keys.size > t - 1 -> { // лист имеет ПРАВОГО брата, который может поделится с ним ключом(merge не требуется).

                        node.children[x].keys.add(node.keys[x])
                        node.keys[x] = node.children[x + 1].keys.first()
                        node.children[x + 1].keys.removeAt(0)

                        if (!node.children[x].Leaf) {

                            node.children[x].children.add(node.children[x + 1].children.first())
                            node.children[x + 1].children.removeAt(0)

                        }

                    }


                    node.children[x] != node.children.first() && node.children[x - 1].keys.size > t - 1 -> { // лист имеет ЛЕВОГО брата, который может поделится с ним ключом(merge не требуется).

                        node.children[x].keys.add(0, node.keys[x - 1])
                        node.keys[x - 1] = node.children[x - 1].keys.last()
                        node.children[x - 1].keys.removeAt(node.children[x - 1].keys.size - 1)

                        if (!node.children[x].Leaf) {

                            node.children[x].children.add(0, node.children[x - 1].children.last())
                            node.children[x - 1].children.removeAt(node.children[x - 1].children.size - 1)

                        }
                    }


                    node.children[x] != node.children.last() -> { //имеет ПРАВОГО брата, но тот не поделится(merge).

                        node.merge(x)

                        if (node.keys.isEmpty()) {

                            root = node.children[x]

                        }

                    }


                    node.children[x] != node.children.first() -> { //имеет ЛЕВОГО брата, но тот не поделится(merge).

                        node.merge(x - 1)

                        if (node.keys.isEmpty()) {

                            root = node.children[x - 1]

                        }

                        x--
                    }
                }
            }//end of the second 'if'

            deleter(key, node.children[x])
        }
    }

        private fun NearestLeftSon(key: K, node: Node<K>): Node<K> {

        var result = node.children[node.keys.indexOf(key)]

        while (!result.Leaf) result = result.children.last()

        return result

    }

        private fun NearestRightSon(key: K, node: Node<K>): Node<K> {

        var result = node.children[node.keys.indexOf(key) + 1]

        while (!result.Leaf) result = result.children.first()

        return result

    }


    private fun findPosition(key: K, node: Node<K>): Int {

        for (x in 0..node.keys.size - 1){

            if (key > node.keys[x])

            else return x

        }

        return node.keys.size
    }


    fun find(key: K): Boolean {

        var node: Node<K>? = root

        if (node == null) return false

        var x = findPosition(key, node)

        if (x == node.keys.size) x -= 1


        while (node!!.keys[x] != key) {

            if (node.Leaf) return false

            when {

                key < node.keys[x] -> node = node.children[x]

                key > node.keys[x] -> node = node.children[x + 1]

            }

            x = findPosition(key, node)

            if (x == node.keys.size) x -= 1

        }

        return true
    }

    override fun iterator(): Iterator<Node<K>> {

        return (object : Iterator<Node<K>> {

            var NodeList = LinkedList<Node<K>>()

            init {

                if (root != null && root!!.keys.isNotEmpty()) {

                    NodeList.add(root!!)

                }

            }

            override fun hasNext() = NodeList.isNotEmpty()

            override fun next(): Node<K> {

                val next = NodeList.remove()

                if (!next.Leaf) {

                    NodeList.addAll(next.children)

                }

                return next
            }
        })
    }

}






















