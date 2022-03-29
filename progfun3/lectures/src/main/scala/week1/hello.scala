package week1

class HelloThread extends Thread {
  override def run(): Unit = {
    println("Hello world!")
  }
}

private val x = new AnyRef()
private var uidCount = 0L

def getUniqueId(): Long = x.synchronized {
  uidCount = uidCount + 1
  uidCount
}

def startThread() = {
  val t = new Thread {
    override def run(): Unit = {
      val uids = for (i <- 0 until 10) yield getUniqueId()
      println(uids)
    }
  }
  t.start()
  t
}

//@main def hello = {
//  val t = new week1.HelloThread
//
//  t.start()
//  t.join()
//}

//@main def ids = {
//  startThread()
//  startThread()
//}
