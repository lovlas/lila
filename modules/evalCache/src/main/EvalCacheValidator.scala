package lila.evalCache

private object Validator {

  case class Error(message: String) extends AnyVal

  def apply(in: EvalCacheEntry.Input): Option[Error] =
    in.eval.pvs.list.foldLeft(none[Error]) {
      case (None, pv) => chess.Replay.boardsFromUci(
        pv.moves.value.list,
        in.fen.some,
        in.id.variant
      ).fold(err => Error(err.shows).some, _ => none)
      case (error, _) => error
    }
}
