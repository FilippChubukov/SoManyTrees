
class RBT<K: Comparable<K>, V>: Interface<K, V>, Iterable<Pair<K, V>>  {

    var root: Node<K, V>? = null

    override fun insert(key: K, value: V){

        var current: Node<K, V>? = root

        var par: Node<K, V>? = null

        while( current != null ) {

            par = current

            when {

                key < current.key -> current = current.left

                key > current.key -> current = current.right

                key == current.key -> {

                    current.value = value
                    return

                }

            }
        }
        if (par == null) {

            root = Node(key, value, par, true)
            return

        }

        if (key < par.key) {

            par.left = Node(key, value, par, false)
            Balance(par.left)

        }

        else {

            par.right = Node(key, value, par, false)
            Balance(par.right)

        }
    }

        private fun Balance(GetNode: Node<K, V>?) {

        var current: Node<K, V>? = GetNode
        var uncle: Node<K, V>? = null

        while (current?.parent?.Black == false) {

            if (current.parent == current.parent?.parent?.left) {

                uncle = current.parent?.parent?.right

                when {

                    uncle?.Black == false -> {

                        current.parent?.Black = true
                        uncle.Black = true
                        current.parent?.parent?.Black = false
                        current = current.parent?.parent

                    }

                    current == current.parent?.right -> {

                        current = current.parent

                        if (current!!.parent?.parent == null) root = current.parent

                        current.Lrotate()

                    }

                    current == current.parent?.left -> {

                        if (current.parent?.parent?.parent == null) root = current.parent

                        current.parent?.parent?.Rrotate()

                    }
                }
            }

            else {

                uncle = current.parent?.parent?.left


                when {

                    uncle?.Black == false -> {

                        current.parent?.Black = true
                        uncle.Black = true
                        current.parent?.parent?.Black = false
                        current = current.parent?.parent

                    }

                    current == current.parent?.left -> {

                        current = current.parent
                        if (current!!.parent?.parent == null) root = current.parent

                        current.Rrotate()

                    }

                    current == current.parent?.right -> {

                        if (current.parent?.parent?.parent == null) root = current.parent

                        current.parent?.parent?.Lrotate()

                    }
                }
            }
        }
        root?.Black = true
    }

    override fun find(key: K): Pair<K, V>? {

        val FindResult = FindPrivate(key)

        when {

            FindResult == null -> return null

            else -> return Pair(FindResult.key, FindResult.value)

        }

    }

        private fun FindPrivate(key: K): Node<K, V>? {

        var current = root

        while (current != null) {

            when {

                key == current.key -> return current

                key < current.key -> current = current.left

                else  -> current = current.right

            }
        }
        return null
    }

    override fun delete(key: K) {
        val node = FindPrivate(key)

        if (node == null) return

        delNode(node)
    }

        private fun delNode(node: Node<K, V>) {

        val prev = findMax(node.left)

        when {

            (node == root && node.Leaf()) -> {

                root = null
                return

            }

            (node.right != null && node.left != null) -> {

                node.key = prev!!.key
                node.value = prev.value
                delNode(prev)
                return

            }

            (!node.Black && node.Leaf()) -> {

                if (node == node.parent!!.left)
                    node.parent!!.left = null
                else
                    node.parent!!.right = null

                return

            }

            (node.Black && node.left != null && !node.left!!.Black) -> {

                node.key = node.left!!.key
                node.value = node.left!!.value
                node.left = null
                return

            }

            (node.Black && node.right != null && !node.right!!.Black) -> {

                node.key = node.right!!.key
                node.value = node.right!!.value
                node.right = null
                return

            }

            else -> deleteCase1(node)

        }

        if (node == node.parent!!.left)
            node.parent!!.left = null
        else
            node.parent!!.right = null
    }

        private fun deleteCase1(node: Node<K, V>) {
        if (node.parent != null) deleteCase2(node)
    }

        private fun deleteCase2(node: Node<K, V>) {

        val brother = node.FindBrother()

        if (!brother!!.Black) {

            if (node == node.parent!!.left) node.parent!!.Lrotate()

            else if (node == node.parent!!.right) node.parent!!.Rrotate()

            if (root == node.parent) root = node.parent!!.parent

        }

        deleteCase3(node)
    }

        private fun deleteCase3(node: Node<K, V>) {
        val brother = node.FindBrother()

        val a: Boolean = brother!!.left == null || brother.left!!.Black
        val b: Boolean = brother.right == null || brother.right!!.Black

        if (a && b && brother.Black && node.parent!!.Black) {

            brother.Black = false
            deleteCase1(node.parent!!)

        }

        else deleteCase4(node)
    }

        private fun deleteCase4(node: Node<K, V>) {
        val brother = node.FindBrother()

        val a: Boolean = brother!!.left == null || brother.left!!.Black
        val b: Boolean = brother.right == null || brother.right!!.Black

        if (a && b && brother.Black && !node.parent!!.Black) {

            brother.Black = false
            node.parent!!.Black = true

        }

        else deleteCase5(node)
    }

        private fun deleteCase5(node: Node<K, V>) {
        val brother = node.FindBrother()

        val a: Boolean = brother!!.left == null || brother.left!!.Black
        val b: Boolean = brother.right == null || brother.right!!.Black

        if (brother.Black) {

            if (brother.left?.Black == false && b && node == node.parent?.left) brother.Rrotate()

            else if (brother.right?.Black == false && a && node == node.parent?.right) brother.Lrotate()

        }

        deleteCase6(node)
    }

        private fun deleteCase6(node: Node<K, V>) {

        val brother = node.FindBrother()

        if (node == node.parent!!.left) {

            brother?.right?.Black = true
            node.parent?.Lrotate()

        }
        else {

            brother?.left?.Black = true
            node.parent?.Rrotate()

        }

        if (root == node.parent)
            root = node.parent!!.parent
    }


    override fun iterator(): Iterator<Pair<K, V>> {
        return (object: Iterator<Pair<K, V>> {
            var node = findMax(root)
            var next = findMax(root)
            val last = findMin(root)

            override fun hasNext(): Boolean {
                return node != null && node!!.key >= last!!.key
            }

            override fun next(): Pair<K, V> {
                next = node
                node = nextSmaller(node)
                return Pair(next!!.key, next!!.value)
            }
        })
    }


    private fun nextSmaller(node: Node<K, V>?): Node<K, V>? {

        var smaller = node

        if (smaller == null) return null

        if (smaller.left != null) return findMax(smaller.left!!)

        else if (smaller == smaller.parent?.left) {

            while (smaller == smaller!!.parent?.left)

                smaller = smaller.parent!!


        }

        return smaller.parent
    }

    private fun findMin(node: Node<K, V>?): Node<K, V>? {

        if (node?.left == null) return node

        else return findMin(node.left)

    }

    private fun findMax(node: Node<K, V>?): Node<K, V>? {

        if (node?.right == null) return node

        else return findMax(node.right)
    }




}