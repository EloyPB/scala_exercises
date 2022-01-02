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


def twoInputGate((Boolean, Boolean) => Boolean)(in1: Wire, in2: Wire, output: Wire): Unit =
  def andAction(): Unit =
    val in1Sig = in1.getSignal()
    val in2Sig = in2.getSignal()
    afterDelay(AndGateDelay) { output.setSignal(in1Sig & in2Sig) }
  in1.addAction(andAction())
  in2.addAction(andAction())


def andGate(in1: Wire, in2: Wire, output: Wire): Unit =
  def andAction(): Unit =
    val in1Sig = in1.getSignal()
    val in2Sig = in2.getSignal()
    afterDelay(AndGateDelay) { output.setSignal(in1Sig & in2Sig) }
  in1.addAction(andAction())
  in2.addAction(andAction())


def andGate(in1: Wire, in2: Wire, output: Wire): Unit =
  def andAction(): Unit =
    val in1Sig = in1.getSignal()
    val in2Sig = in2.getSignal()
    afterDelay(AndGateDelay) { output.setSignal(in1Sig & in2Sig) }
  in1.addAction(andAction())
  in2.addAction(andAction())