class BSTPrinter<K: Comparable<K>, V> {

    fun TreePrinter(tree: Interface<K, V>) {

             NodePrinter(node = (tree as BST<K, V>).root)

    }


    fun NodePrinter(height: Int = 0, node: Node<K, V>?) {

        if (node == null) return

        NodePrinter(height + 1, node.right)

        for (i in 1..height) print(" |")

        println("${node.key}"  + node.value)

        NodePrinter(height + 1, node.left)

    }
}
