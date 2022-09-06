package observatory

trait VisualizationTest extends MilestoneSuite:
  private val milestoneTest = namedMilestoneTest("raw data display", 2) _

  // Implement tests for the methods of the `Visualization` object

  test("Great-circle distance for antipodes is correct"){
    import scala.math.Pi
    val l1 = Location(10, 20)
    val l2 = Location(-10, -160)
    assertEquals(Visualization.greatCircleDistance(l1, l2), 6371*Pi)
  }

  test("Great-circle distance for the same location is 0") {
    val l1 = Location(10, 20)
    assertEquals(Visualization.greatCircleDistance(l1, l1), 0.0)
  }

  test("Interpolate between two temperatures") {
    val temperatures = List((Location(0, -10), 10.0), (Location(0, 10), 20.0))
    assertEquals(Visualization.predictTemperature(temperatures, Location(0, 0)), 15.0)
  }

  test("Temperature of location very close to a station") {
    val temperatures = List((Location(0, -10), 10.0), (Location(0, 10), 20.0))
    assertEquals(Visualization.predictTemperature(temperatures, Location(0, 10.001)), 20.0)
  }