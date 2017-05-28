
fun main(args: Array<String>) {

    val bst = BST<Int, Char>()

    println("f to find")
    println("d to delete")
    println("p to print")
    println("key to insert")

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

            "p" -> Printer<Int, Char>().printTree(bst)


            "d" -> {

                println("Key to delete: ")

                val key = readLine()?.toIntOrNull()

                if (key != null) bst.delete(key)

                else println ("You didn't give key.")

            }

            "q" -> return

            else -> {

                val key = operation?.toIntOrNull()

                val value: Char = 'a'

                if (key != null) {

                    if (bst.find(key) != null) {

                        println("This key already in tree.")

                    } else bst.insert(key, value)

                } else println ("You didn't give key.")


            }
        }
    }
}