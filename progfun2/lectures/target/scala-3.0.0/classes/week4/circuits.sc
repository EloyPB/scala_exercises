trait Simulation:
  type Action = () => Unit

  def currentTime: Int
  def afterDelay(delay: Int)(block: => Unit): Unit
  def run(): Unit
  

class Wire:
  private var sigVal = false
  private var actions: List[Action] = List()

  def getSignal(): Boolean = sigVal

  def setSignal(s: Boolean): Unit =
    if s != sigVal then
      sigVal = s
      for a <- actions do a()

  def addAction(a: Action): Unit =
    actions = a :: actions
    a()


def inverter(input: Wire, output: Wire): Unit =
  def invertAction(): Unit =
    val inputSig = input.getSignal()
    afterDelay(InterverDelay) { output.setSignal(!inputSig) }
  input.addAction(invertAction())

def andGate(in1: Wire, in2: Wire, output: Wire): Unit =
  def andAction(): Unit =
    val in1Sig = in1.getSignal()
    val in2Sig = in2.getSignal()
    afterDelay(AndGateDelay) { output.setSignal(in1Sig & in2Sig) }
  in1.addAction(andAction())
  in2.addAction(andAction())


def orGate(in1: Wire, in2: Wire, output: Wire): Unit =
  def orAction(): Unit =
    val in1Sig = in1.getSignal()
    val in2Sig = in2.getSignal()
    afterDelay(OrGateDelay) { output.setSignal(in1Sig | in2Sig) }
  in1.addAction(orAction())
  in2.addAction(orAction())



trait Gates extends Simulation:
  def InverterDelay: Int
  def AndGateDelay: Int
  def OrGateDelay: Int

  class Wire
  def inverter(input: Wire, output: Wire): Unit
  def andGate(in1: Wire, in2: Wire, output: Wire): Unit
  def orGate(in1: Wire, in2: Wire, output: Wire): Unit


trait Circuits extends Gates:
  def halfAdder(a: Wire, b: Wire, s: Wire, c: Wire): Unit =
    val d = Wire()
    val e = Wire()
    orGate(a, b, d)
    andGate(a, b, c)
    inverter(c, e)
    andGate(d, e, s)



