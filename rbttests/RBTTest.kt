package RBT

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


internal class RBTTest {

    val rbt = RBT<Int, Int>()

    var finalBlackHeight = -1

    fun CheckProperties(node: Node<Int, Int>? = rbt.root, BranchBlackHeight: Int = 0): Boolean {

        if (node == null) return true

        if (node.Leaf()) {

            if (finalBlackHeight == -1) finalBlackHeight = BranchBlackHeight

            if (finalBlackHeight != BranchBlackHeight) return false

            return true
        }

        if (!node.Black) {

            if (node.left?.Black == false || node.right?.Black == false) return false

            else return CheckProperties(node.left, BranchBlackHeight + 1) && CheckProperties(node.right, BranchBlackHeight + 1)
        }

        else {

            val LeftCheck: Boolean
            val RightCheck: Boolean

            if (node.left?.Black == true) LeftCheck = CheckProperties(node.left, BranchBlackHeight + 1)

            else LeftCheck = CheckProperties(node.left, BranchBlackHeight)

            if (node.right?.Black == true) RightCheck = CheckProperties(node.right, BranchBlackHeight + 1)

            else RightCheck = CheckProperties(node.right, BranchBlackHeight)

            return LeftCheck && RightCheck

        }
    }

    @Test
         fun find() {

            for (i in 1..100) rbt.insert(i, i)

            for (i in 1..100) assertEquals(rbt.find(i), Pair(i, i))
         }

    @Test
        fun Nofind() {

            for (i in 1..100) rbt.insert(i, i)

            assertNull(rbt.find(0))
            assertNull(rbt.find(101))
        }

    @Test
         fun insertSaveProperties() {

            for (i in 1..100) {

                rbt.insert(i, i)
                finalBlackHeight = -1

                assertTrue(CheckProperties())

            }
         }

    @Test
        fun deleteAndSearch() {

            rbt.insert(1, 1)
            rbt.delete(1)

            assertNull(rbt.root)

        for (i in 1..100) rbt.insert(i, i)

        for (i in 30..60) rbt.delete(i)

        for (i in 1..100) {

            if (i in 30..60) assertFalse(Pair(i, i) in rbt)

            else assertTrue(Pair(i, i) in rbt)

        }
    }




    @Test
        fun deleteSaveProperties() {

            for (i in 1..100) rbt.insert(i, i)

            for (i in 1..100) {

                rbt.delete(i)
                finalBlackHeight = -1

                assertTrue(CheckProperties())

            }
        }
}













