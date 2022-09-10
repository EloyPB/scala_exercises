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

  test("Colors of -70ºC and -60ºC are the same") {
    val points = List((60.0, Color(255, 255, 255)), (32.0, Color(255, 0, 0)), (12.0, Color(255, 255, 0)),
      (0.0, Color(0, 255, 255)), (-15.0, Color(0, 0, 255)), (-27.0, Color(255, 0, 255)), (-50.0, Color(33, 0, 107)),
      (-60.0, Color(0, 0, 0)))
    assertEquals(Visualization.interpolateColor(points, -70), Color(0, 0, 0))
  }

  test("Color of -55ºC is correctly interpolated") {
    val points = List((60.0, Color(255, 255, 255)), (32.0, Color(255, 0, 0)), (12.0, Color(255, 255, 0)),
      (0.0, Color(0, 255, 255)), (-15.0, Color(0, 0, 255)), (-27.0, Color(255, 0, 255)), (-50.0, Color(33, 0, 107)),
      (-60.0, Color(0, 0, 0)))
    assertEquals(Visualization.interpolateColor(points, -55.0), Color(16, 0, 53))
  }