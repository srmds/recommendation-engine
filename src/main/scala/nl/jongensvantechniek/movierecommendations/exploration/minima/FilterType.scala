package nl.jongensvantechniek.movierecommendations.exploration.minima

import nl.jongensvantechniek.movierecommendations.exploration.minima

object FilterType extends Enumeration {
  type FilterType = Value

  val MIN_TEMPERATURE: minima.FilterType.Value = Value("TMIN")
  val MAX_TEMPERATURE: minima.FilterType.Value = Value("TMAX")
}