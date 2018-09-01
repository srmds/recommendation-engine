package nl.jongensvantechniek.movierecommendations.service.minima

import nl.jongensvantechniek.movierecommendations.service.minima

object FilterType extends Enumeration {
  type FilterType = Value

  val MIN_TEMPERATURE: minima.FilterType.Value = Value("TMIN")
  val MAX_TEMPERATURE: minima.FilterType.Value = Value("TMAX")
}