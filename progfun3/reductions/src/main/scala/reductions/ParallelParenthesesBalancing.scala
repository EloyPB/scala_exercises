package reductions

import scala.annotation.*
import org.scalameter.*

object ParallelParenthesesBalancingRunner:

  @volatile var seqResult = false

  @volatile var parResult = false

  val standardConfig = config(
    Key.exec.minWarmupRuns := 40,
    Key.exec.maxWarmupRuns := 80,
    Key.exec.benchRuns := 120,
    Key.verbose := false
  ) withWarmer(Warmer.Default())

  def main(args: Array[String]): Unit =
    val length = 1000
    val chars = new Array[Char](length)
    val threshold = 100
    val seqtime = standardConfig measure {
      seqResult = ParallelParenthesesBalancing.balance(chars)
    }
    println(s"sequential result = $seqResult")
    println(s"sequential balancing time: $seqtime")

    val fjtime = standardConfig measure {
      parResult = ParallelParenthesesBalancing.parBalance(chars, threshold)
    }
    println(s"parallel result = $parResult")
    println(s"parallel balancing time: $fjtime")
    println(s"speedup: ${seqtime.value / fjtime.value}")

object ParallelParenthesesBalancing extends ParallelParenthesesBalancingInterface:

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
   */
  def balance(chars: Array[Char]): Boolean =
    def balanceLoop(chars: Array[Char], open: Int): Boolean =
      if open < 0 then false
      else if chars.isEmpty then open == 0
      else {
        val inc = if chars(0) == '(' then 1 else if chars(0) == ')' then -1 else 0
        balanceLoop(chars.tail, open + inc)
      }
    balanceLoop(chars, 0)

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
   */
  def parBalance(chars: Array[Char], threshold: Int): Boolean =

    def traverse(idx: Int, until: Int, open: Int, close: Int): (Int, Int) = {
      if idx == until then (open, close)
      else {
        val incO = if chars(idx) == '(' then 1 else if chars(idx) == ')' then -1 else 0
        val incC = if chars(idx) == ')' & open == 0 then 1 else 0
        traverse(idx + 1, until, 0.max(open + incO), close + incC)
      }
    }

    def reduce(from: Int, until: Int): (Int, Int) = {
      if until - from <= threshold then traverse(from, until, 0, 0)
      else {
        val mid = (until - from) / 2 + from
        val (s1, s2) = parallel(reduce(from, mid), reduce(mid, until))
        val open = 0.max(s1(0) - s2(1)) + s2(0)
        val close = s1(1) + 0.max(s2(1) - s1(0))
        (open, close)
      }
    }

    reduce(0, chars.length) == (0, 0)

  // For those who want more:
  // Prove that your reduction operator is associative!

