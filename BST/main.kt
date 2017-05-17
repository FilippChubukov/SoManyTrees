
fun main(args: Array<String>) {

    val bst = BST<Int, Char>()

    while (true) {

        val operation =readLine()

        when (operation) {

            "f" -> {

                print("Key to find: ")

                val key = readLine()?.toIntOrNull()

                if (key != null) {

                    if (bst.find(key) != null) println("Found")

                    else println("Not found")

                }
            }

            "p" -> BSTPrinter<Int, Char>().TreePrinter(bst)


            "d" -> {

                println("Key to delete: ")

                val key = readLine()?.toIntOrNull()

                if (key != null) bst.delete(key)
            }

            "q" -> return

            else -> {

                println("Value for Node: ")

                val values: CharArray? = readLine()?.toCharArray()
                val value: Char = values!![0]

                val key = operation?.toIntOrNull()

                if (key != null) bst.insert(key, value)

            }
        }
    }
}