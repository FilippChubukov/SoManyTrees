package RBT

fun main(args: Array<String>) {

    val rbt = RBT<Int, Char>()

    println("f to find")
    println("d to delete")
    println("p to print")
    println("key to insert")

    while (true) {

        val operation = readLine()

        when (operation) {

            "f" -> {

                print("Key to find: ")

                val key = readLine()?.toIntOrNull()

                if (key != null) {

                    if (rbt.find(key) != null) println("Key found")

                    else println("Key not found")

                } else println ("You didn't give key.")

            }

            "p" -> Printer<Int, Char>().printTree(rbt)


            "d" -> {

                println("Key to delete: ")

                val key = readLine()?.toIntOrNull()

                if (key != null) rbt.delete(key)

                else println ("You didn't give key.")

            }

            "q" -> return

            else -> {

                val value : Char = 'a'

                val key = operation?.toIntOrNull()

                if (key != null) rbt.insert(key, value)

            }
        }
    }
}