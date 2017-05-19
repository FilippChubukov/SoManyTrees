
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

                } else println ("You didn't give key.")


            }

            "p" -> BSTPrinter<Int, Char>().TreePrinter(bst)


            "d" -> {

                println("Key to delete: ")

                val key = readLine()?.toIntOrNull()

                if (key != null) bst.delete(key)

                else println ("You didn't give key.")

            }

            "q" -> return

            else -> {

                val value: Char = 'a'

                val key = operation?.toIntOrNull()

                if (key != null) bst.insert(key, value)

                else println ("You didn't give key.")

            }
        }
    }
}