package nl.jongensvantechniek.movierecommendations.service.social

trait OccurenceCounter {

  // Function to extract the hero ID and number of connections from each line
  def countCoOccurences(line: String) = {
    var elements = line.split("\\s+")
    ( elements(0).toInt, elements.length - 1 )
  }

}
