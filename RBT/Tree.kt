
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



        when {

            (node == root && node.Leaf()) -> { // Узел является единственным в дереве.

                root = null
                return

            }

            (node.right != null && node.left != null) -> { // Есть правый и левый потомки.

                val shifter = findmax(node.left)

                node.key = shifter!!.key
                node.value = shifter.value

                delNode(shifter)

                return

            }

            (!node.Black && node.Leaf()) -> { // Узел красный и является листом.

                if (node == node.parent!!.left) node.parent!!.left = null

                else node.parent!!.right = null

                return

            }

            (node.Black && node.left != null && !node.left!!.Black) -> { // Узел черный, имеет левого красного потомка.

                node.key = node.left!!.key
                node.value = node.left!!.value

                node.left = null

                return

            }

            (node.Black && node.right != null && !node.right!!.Black) -> { // Узел черный, имеет красного правого потомка.

                node.key = node.right!!.key
                node.value = node.right!!.value

                node.right = null

                return

            }

            else -> situation1(node)

        }

        if (node == node.parent!!.left) node.parent!!.left = null

        else node.parent!!.right = null
    }

        private fun situation1(node: Node<K, V>) {

        if (node.parent != null) situation2(node)

    }

        private fun situation2(node: Node<K, V>) {

        val brother = node.FindBrother()

        if (!brother!!.Black) {

            if (node == node.parent!!.left) node.parent!!.Lrotate()

            else node.parent!!.Rrotate()

            if (root == node.parent) root = node.parent!!.parent

        }

            situation3(node)
    }

        private fun situation3(node: Node<K, V>) {

        val brother = node.FindBrother()

        val BrotherLeftSonIsblack: Boolean = brother!!.left == null || brother.left!!.Black
        val BrotherRightSonIsblack: Boolean = brother.right == null || brother.right!!.Black

        if (BrotherLeftSonIsblack && BrotherRightSonIsblack && brother.Black && node.parent!!.Black) {

            brother.Black = false

            situation1(node.parent!!)

        }

        else situation4(node)
    }

        private fun situation4(node: Node<K, V>) {

        val brother = node.FindBrother()

        val BrotherLeftSonIsblack: Boolean = brother!!.left == null || brother.left!!.Black
        val BrotherRightSonIsblack: Boolean = brother.right == null || brother.right!!.Black

        if (BrotherLeftSonIsblack && BrotherRightSonIsblack && brother.Black && !node.parent!!.Black) {

            brother.SwapColors(node.parent)

        }

        else situation5(node)
    }

        private fun situation5(node: Node<K, V>) {

        val brother = node.FindBrother()

        val BrotherLeftSonIsblack: Boolean = brother!!.left == null || brother.left!!.Black
        val BrotherRightSonIsblack: Boolean = brother.right == null || brother.right!!.Black



            if (brother.Black && brother.left?.Black == false && BrotherRightSonIsblack && node == node.parent?.left){

                brother.Rrotate()

            }

            else if (brother.Black && brother.right?.Black == false && BrotherLeftSonIsblack && node == node.parent?.right) {

                brother.Lrotate()

            }


            situation6(node)
    }

        private fun situation6(node: Node<K, V>) {

        val brother = node.FindBrother()

        if (node == node.parent!!.left) {

            brother?.right?.Black = true

            node.parent?.Lrotate()

        }

        else {

            brother?.left?.Black = true

            node.parent?.Rrotate()

        }

        if (root == node.parent) root = node.parent!!.parent
    }


    override fun iterator(): Iterator<Pair<K, V>> {
        return (object: Iterator<Pair<K, V>> {
            var node = findmax(root)
            var following = findmax(root)
            val last = findmin(root)

            override fun hasNext(): Boolean {

                return node != null && node!!.key >= last!!.key

            }

            override fun next(): Pair<K, V> {
                following = node
                node = findMaxSmaller(node)

                return Pair(following!!.key, following!!.value)
            }
        })
    }

    private fun findMaxSmaller(node: Node<K, V>?): Node<K, V>? {

        var smaller = node

        if (smaller == null) return null

        if (smaller.left != null) return findmax(smaller.left!!)

        else if (smaller == smaller.parent?.left) {

            while (smaller == smaller!!.parent?.left) {

                smaller = smaller.parent!!

            }
        }

        return smaller.parent
    }

    private fun findmin(node: Node<K, V>?): Node<K, V>? {

        if (node?.left == null) return node

        else return findmin(node.left)

    }

    private fun findmax(node: Node<K, V>?): Node<K, V>? {

        if (node?.right == null) return node

        else return findmax(node.right)
    }




}