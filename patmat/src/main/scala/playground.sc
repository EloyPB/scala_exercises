import patmat.{CodeTree, Fork, Leaf}

import java.nio.charset.CoderResult

val pair: (Char, Int) = ('a', 2)
pair._1
pair._2
pair(1)

pair.toList.contains('a')

val counts: List[(Char, Int)] = List(('a', 1), ('b', 2), ('c', 3))
counts(0|2)

val nums = 1 :: 2 :: Nil

val list: List[(Char, Int)] = List(pair)
list ::: List(pair)

list.filter(p => p(0) == 'b')

def times(chars: List[Char]): List[(Char, Int)] = {
  def walker(chars: List[Char], counts: List[(Char, Int)]): List[(Char, Int)] = {
    if (chars.isEmpty) counts
    else {
      val count = counts.filter(p => p(0) == chars.head)
      if (count.isEmpty) walker(chars.tail, counts ::: List((chars.head, 1)))
      else walker(chars.tail, counts.filter(p => p(0) != chars.head) ::: List((chars.head, count(0)(1) + 1)))
    }
  }
  walker(chars, List())
}

val freqs = times(List('a', 'b', 'a', 'c', 'b'))

def makeOrderedLeafList(freqs: List[(Char, Int)]): List[Leaf] = {
  def walker(freqs: List[(Char, Int)], leaves: List[Leaf]): List[Leaf] = {
    if (freqs.isEmpty) leaves
    else walker(freqs.tail, leaves ::: List(Leaf(freqs.head(0), freqs.head(1))))
  }
  implicit val leafOrdering: Ordering[Leaf] = Ordering.by(_.weight)
  walker(freqs, List()).sorted
}

val leaves = makeOrderedLeafList(freqs)

def weight(tree: CodeTree): Int = tree match {
  case l: Leaf => l.weight
  case f: Fork => f.weight
}

def chars(tree: CodeTree): List[Char] = tree match {
  case l: Leaf => List(l.char)
  case f: Fork => f.chars
}

def singleton(trees: List[CodeTree]): Boolean = trees.length < 2

def combine(trees: List[CodeTree]): List[CodeTree] = {
  def insert(tree: CodeTree, trees: List[CodeTree]): List[CodeTree] = {
    if (trees.isEmpty) List(tree)
    else if (weight(tree) > weight(trees.head)) List(trees.head) ::: insert(tree, trees.tail)
    else List(tree) ::: trees
  }
  if (singleton(trees)) trees
  else {
    val fork = Fork(trees(0), trees(1), chars(trees(0)) ::: chars(trees(1)), weight(trees(0)) + weight(trees(1)))
    insert(fork, trees.tail.tail)
  }
}

combine(leaves)

def until(done: List[CodeTree] => Boolean, merge: List[CodeTree] => List[CodeTree])(trees: List[CodeTree]): List[CodeTree] = {
  if (done(trees)) trees
  else until(done, merge)(merge(trees))
}

def createCodeTree(chars: List[Char]): CodeTree = {
  val leaves: List[CodeTree] = makeOrderedLeafList(times(chars))
  until(singleton, combine)(leaves)(0)
}

createCodeTree(List('a', 'a', 'b', 'c'))

type Bit = Int

/**
 * This function decodes the bit sequence `bits` using the code tree `tree` and returns
 * the resulting list of characters.
 */
def decode(tree: CodeTree, bits: List[Bit]): List[Char] = {
  def nextChar(tree: CodeTree, bits: List[Bit]): (List[Bit], Char) = {
    tree match {
      case l: Leaf => (bits, l.char)
      case f: Fork => if (bits.head == 1) nextChar(f.right, bits.tail) else nextChar(f.left, bits.tail)
    }
  }
  def walker(tree: CodeTree, bits: List[Bit], decoded: List[Char]): List[Char] = {
    if (bits.isEmpty) decoded
    else {
      val pair = nextChar(tree, bits)
      walker(tree, pair(0), decoded ::: List(pair(1)))
    }
  }
  walker(tree, bits, List())
}

/**
 * A Huffman coding tree for the French language.
 * Generated from the data given at
 *   http://fr.wikipedia.org/wiki/Fr%C3%A9quence_d%27apparition_des_lettres_en_fran%C3%A7ais
 */
val frenchCode: CodeTree = Fork(Fork(Fork(Leaf('s',121895),Fork(Leaf('d',56269),Fork(Fork(Fork(Leaf('x',5928),Leaf('j',8351),List('x','j'),14279),Leaf('f',16351),List('x','j','f'),30630),Fork(Fork(Fork(Fork(Leaf('z',2093),Fork(Leaf('k',745),Leaf('w',1747),List('k','w'),2492),List('z','k','w'),4585),Leaf('y',4725),List('z','k','w','y'),9310),Leaf('h',11298),List('z','k','w','y','h'),20608),Leaf('q',20889),List('z','k','w','y','h','q'),41497),List('x','j','f','z','k','w','y','h','q'),72127),List('d','x','j','f','z','k','w','y','h','q'),128396),List('s','d','x','j','f','z','k','w','y','h','q'),250291),Fork(Fork(Leaf('o',82762),Leaf('l',83668),List('o','l'),166430),Fork(Fork(Leaf('m',45521),Leaf('p',46335),List('m','p'),91856),Leaf('u',96785),List('m','p','u'),188641),List('o','l','m','p','u'),355071),List('s','d','x','j','f','z','k','w','y','h','q','o','l','m','p','u'),605362),Fork(Fork(Fork(Leaf('r',100500),Fork(Leaf('c',50003),Fork(Leaf('v',24975),Fork(Leaf('g',13288),Leaf('b',13822),List('g','b'),27110),List('v','g','b'),52085),List('c','v','g','b'),102088),List('r','c','v','g','b'),202588),Fork(Leaf('n',108812),Leaf('t',111103),List('n','t'),219915),List('r','c','v','g','b','n','t'),422503),Fork(Leaf('e',225947),Fork(Leaf('i',115465),Leaf('a',117110),List('i','a'),232575),List('e','i','a'),458522),List('r','c','v','g','b','n','t','e','i','a'),881025),List('s','d','x','j','f','z','k','w','y','h','q','o','l','m','p','u','r','c','v','g','b','n','t','e','i','a'),1486387)

/**
 * What does the secret message say? Can you decode it?
 * For the decoding use the `frenchCode' Huffman tree defined above.
 */
val secret: List[Bit] = List(0,0,1,1,1,0,1,0,1,1,1,0,0,1,1,0,1,0,0,1,1,0,1,0,1,1,0,0,1,1,1,1,1,0,1,0,1,1,0,0,0,0,1,0,1,1,1,0,0,1,0,0,1,0,0,0,1,0,0,0,1,0,1)

/**
 * Write a function that returns the decoded secret
 */
def decodedSecret: List[Char] = decode(frenchCode, secret)

println(decodedSecret)

def encode(tree: CodeTree)(text: List[Char]): List[Bit] = {
  def nextBits(tree: CodeTree, char: Char, bits: List[Bit]): List[Bit] = {
    tree match {
      case l: Leaf => bits
      case f: Fork =>
        if (chars(f.left).contains(char)) nextBits(f.left, char, bits ::: List(0))
        else nextBits(f.right, char, bits ::: List(1))
    }
  }
  def walker(tree: CodeTree, remainder: List[Char], code: List[Bit]): List[Bit] = {
    if (remainder.isEmpty) code
    else walker(tree, remainder.tail, code ::: nextBits(tree, remainder.head, List()))
  }
  walker(tree, text, List())
}

val text = List('h', 'u', 'f', 'f', 'm', 'a', 'n', 'e', 's', 't', 'c', 'o', 'o', 'l')
val encoded = encode(frenchCode)(text)
encoded == secret

type CodeTable = List[(Char, List[Bit])]

def codeBits(table: CodeTable)(char: Char): List[Bit] = table.find(p => p._1 == char).get(1)

/**
 * This function takes two code tables and merges them into one. Depending on how you
 * use it in the `convert` method above, this merge method might also do some transformations
 * on the two parameter code tables.
 */
def mergeCodeTables(a: CodeTable, b: CodeTable): CodeTable = {
  a.map(p => (p(0), 0 :: p(1))) ::: b.map(p => (p(0), 1 :: p(1)))
}

/**
 * Given a code tree, create a code table which contains, for every character in the
 * code tree, the sequence of bits representing that character.
 *
 * Hint: think of a recursive solution: every sub-tree of the code tree `tree` is itself
 * a valid code tree that can be represented as a code table. Using the code tables of the
 * sub-trees, think of how to build the code table for the entire tree.
 */
def convert(tree: CodeTree): CodeTable = {
  tree match {
    case f: Fork => mergeCodeTables(convert(f.left), convert(f.right))
    case l: Leaf => List((l.char, List()))
  }
}

/**
 * This function encodes `text` according to the code tree `tree`.
 *
 * To speed up the encoding process, it first converts the code tree to a code table
 * and then uses it to perform the actual encoding.
 */
def quickEncode(tree: CodeTree)(text: List[Char]): List[Bit] = {
  def encode(table: CodeTable, text: List[Char], code: List[Bit]): List[Bit] = {
    if (text.isEmpty) code
    else encode(table, text.tail, code ::: codeBits(table)(text.head))
  }
  encode(convert(tree), text, List())
}

quickEncode(frenchCode)(text)
encoded == secret

singleton(List())
