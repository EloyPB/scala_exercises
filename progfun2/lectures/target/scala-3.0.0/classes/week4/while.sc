def repeatUntil(command: => Unit)(condition: => Boolean): Unit =
  command
  if !condition then repeatUntil(command)(condition)


var x = 0
var y = 2
repeatUntil {
  x = x + 1
  y = y * 2
} (x == 5)
y


for i <- 1 until 3 do System.out.print(s"$i ")