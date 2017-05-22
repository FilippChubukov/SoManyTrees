


class BST<K: Comparable<K>, V>: Interface<K, V>, Iterable<Pair<K, V>> {

    var root: Node<K, V>? = null

    override fun insert(key: K, value: V) {

        var current: Node<K,V>? = root
        var parent: Node<K, V>? = null

        while (current!= null) {

            parent = current

            when {

                key < current.key -> current = current.left

                key > current.key -> current = current.right

                key == current.key -> return

            }
        }

        if (parent == null) {

            root = Node(key , value)
        }
        else when {

            key < parent.key -> parent.left = Node(key, value, parent)

            key > parent.key -> parent.right = Node(key, value, parent)

        }
    }


    override fun find (key: K): Pair<K, V>? {

        val FindResult = FindPrivate(key)

        when {

            FindResult == null -> return null

            else -> return Pair(FindResult.key, FindResult.value)
        }
    }

        private fun FindPrivate(key: K): Node<K, V>?  {

        var current = root

        while (current!= null){

            when {

                key == current.key -> return current

                key < current.key -> current = current.left

                key > current.key -> current = current.right
            }
        }
        return null
    }


    override fun delete(key: K) {

        val NeedDeleted: Node<K, V>?
        val delparent: Node<K, V>?

        NeedDeleted = FindPrivate(key)
        if (NeedDeleted == null) return

        else {

            delparent = NeedDeleted.parent

        }

        if(NeedDeleted.left == null && NeedDeleted.right == null) { // Оба потомка нулевые.

            if (NeedDeleted.parent == null) {

                root = null
                return

            }

            when {

                NeedDeleted == delparent?.left -> delparent.left = null

                NeedDeleted == delparent?.right -> delparent.right = null

            }
        }

        else if (NeedDeleted.left == null || NeedDeleted.right == null) { // Только один потомок нулевой.

            if (NeedDeleted.left == null) {

                when {

                    delparent?.left == NeedDeleted -> delparent.left = NeedDeleted.right

                    delparent?.right == NeedDeleted -> delparent.right = NeedDeleted.right

                }

                NeedDeleted.right?.parent = delparent

            } else {

                when {

                    delparent?.left == NeedDeleted -> delparent.left = NeedDeleted.left

                    delparent?.right == NeedDeleted -> delparent.right = NeedDeleted.left

                }

                NeedDeleted.left?.parent = delparent

            }

        }

        else {// Оба потомка не нулевые.

            val shifter: Node<K, V> = findmin(NeedDeleted.right)!!

            NeedDeleted.key = shifter.key

            if (shifter.parent?.left == shifter) {

                shifter.parent?.left = shifter.right

                if (shifter.right != null) shifter.right!!.parent = shifter.parent

            }

            else {

                shifter.parent?.right = shifter.right

                if (shifter.right != null) shifter.right!!.parent = shifter.parent

        }
        }
    }


     override fun iterator(): Iterator<Pair<K, V>> {

        return (object: Iterator<Pair<K, V>> {

            var node = findmax(root)
            var following = findmax(root)
            val last = findmin(root)

            override fun hasNext(): Boolean {

                return (node != null && node!!.key >= last!!.key)

            }

            override fun next(): Pair<K, V> {

                following = node
                node = findMaxSmaller(node)

                return Pair(following!!.key, following  !!.value)

                }
            })
        }

        private fun findMaxSmaller(node: Node<K, V>?): Node<K, V>? {

            if (node == null) return null

            var smaller = node

            if (smaller.left != null) {

                return findmax(smaller.left!!)
            }

            else {

                if (smaller.parent?.left == smaller) {

                    while (smaller == smaller!!.parent?.left)
                        smaller = smaller.parent!!
                }
            }
            return smaller.parent
        }

        private fun findmin(GetNode: Node<K, V>?): Node<K, V>? {

            if (GetNode?.left == null) return GetNode

            else return findmin(GetNode.left)

        }

        private fun findmax(GetNode: Node<K, V>?): Node<K, V>? {

            if (GetNode?.right == null) return GetNode

            else return findmax(GetNode.right)
        }

}





















