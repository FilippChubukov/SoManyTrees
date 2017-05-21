
class Node<K: Comparable<K>> (var Leaf: Boolean = true) { //Leaf = true - лист, Leaf = false - не лист

    var keys = ArrayList<K>()
    var children = ArrayList<Node<K>>()

    fun merge(i: Int) {

        val left = this.children[i]
        val right = this.children[i + 1]
        val key = this.keys[i]

        left.keys.add(key)
        left.keys.addAll(right.keys)

        if (!right.Leaf) left.children.addAll(right.children)

        this.keys.removeAt(i)
        this.children.removeAt(i + 1)

    }

    fun divide(t: Int, i: Int) {

        val NodetoDivide = this.children[i]
        val newNode = Node<K>(NodetoDivide.Leaf)

        for (j in 0..t - 2) {

            newNode.keys.add(NodetoDivide.keys[t])
            NodetoDivide.keys.removeAt(t)

        }


         if (!NodetoDivide.Leaf) for (j in 0..t - 1) {

             newNode.children.add(NodetoDivide.children[t])
             NodetoDivide.children.removeAt(t)

             }


        this.children.add(i + 1, newNode)
        this.keys.add(i, NodetoDivide.keys[t - 1])
        NodetoDivide.keys.removeAt(t - 1)

     }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Node<*>

        if (Leaf != other.Leaf) return false
        if (keys != other.keys) return false
        if (children != other.children) return false

        return true
    }

    override fun hashCode(): Int {
        var result = Leaf.hashCode()
        result = 31 * result + keys.hashCode()
        result = 31 * result + children.hashCode()
        return result
    }


}