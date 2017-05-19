
class Node<K: Comparable<K>, V>(var key: K, var value: V, var parent: Node<K, V>? = null, var Black: Boolean = false) {//Black=true, Red=false

    var right: Node<K, V>? = null

    var left: Node<K, V>? = null

    fun SwapColors(node2: Node<K, V>?) {

        val node1 = this.Black

        if (node2 != null) {

            this.Black = node2.Black
            node2.Black = node1

        }
    }


    fun Lrotate() {

        val rightChild = this.right
        val par = this.parent

        if (rightChild == null) return

        this.SwapColors(rightChild)
        rightChild.left?.parent = this
        this.right = rightChild.left
        rightChild.left = this


        when {

            this == par?.left -> par.left = rightChild

            this == par?.right -> par.right = rightChild

        }


        this.parent = rightChild
        rightChild.parent = par

    }

    fun Rrotate() {

                 val leftChild = this.left

                 val par = this.parent

                 if (leftChild == null) return

                 this.SwapColors(leftChild)
                 leftChild.right?.parent = this
                 this.left = leftChild.right
                 leftChild.right = this


                 when {
                         this == par?.left -> par.left = leftChild
                         this == par?.right -> par.right = leftChild
                     }


                 this.parent = leftChild
                 leftChild.parent = par

    }


    fun Leaf(): Boolean { //This node is Leaf - true, else - false

        return ((this.left == null) && (this.right == null))

    }


    fun FindBrother(): Node<K, V>? {

        when {

            this == this.parent?.left -> return this.parent!!.right

            else -> return this.parent?.left

        }
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Node<*, *>

        if (key != other.key) return false
        if (value != other.value) return false
        if (parent != other.parent) return false
        if (Black != other.Black) return false
        if (right != other.right) return false
        if (left != other.left) return false

        return true
    }

    override fun hashCode(): Int {
        var result = key.hashCode()
        result = 31 * result + (value?.hashCode() ?: 0)
        result = 31 * result + (parent?.hashCode() ?: 0)
        result = 31 * result + Black.hashCode()
        result = 31 * result + (right?.hashCode() ?: 0)
        result = 31 * result + (left?.hashCode() ?: 0)
        return result
    }


}

