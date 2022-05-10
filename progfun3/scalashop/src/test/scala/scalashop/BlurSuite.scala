package scalashop

import java.util.concurrent.*
import scala.collection.*

class BlurSuite extends munit.FunSuite:
  // Put tests here
  test("box blur kernel"){
    val orange = rgba(255, 153, 51, 255)
    val img = Img(4, 4, Array.fill(16)(orange))
    assertEquals(orange, boxBlurKernel(img, 1, 1, 1))
  }
