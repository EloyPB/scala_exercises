object Fingers extends Enumeration {
  type Finger = Value
  val Thumb, Index, Middle, Ring, Little = Value
}

import Fingers._

def isShortest(finger: Finger) = finger == Little

isShortest(Little)