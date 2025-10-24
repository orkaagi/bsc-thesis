import {Round} from "../../App";

import "../style.css";

function getSpanForSymbol(suit: string) {
  if (suit == "SPADES") {
    return <span className="card-suit p-0.5">&spades;</span>;
  }
  if (suit == "HEARTS") {
    return <span className="card-suit p-0.5">&hearts;</span>;
  }
  if (suit == "DIAMONDS") {
    return <span className="card-suit p-0.5">&#x25C6;</span>;
  }
  if (suit == "CLUBS") {
    return <span className="card-suit p-0.5">&clubs;</span>;
  }
  return <span className="card-suit p-0.5"></span>;
}

function CurrentRound({
                        starterIndex,
                        currentIndex,
                        isNewRound,
                        round,
                        positions,
                      }: Round) {
  return (
    <>
      <section id="current-round" className="container m-2 p-2 gap-1 flex-grow-1">
        <div className={"row"}>
          <div
            className={"col flex justify-center items-center flex-grow-0 " + round[3].first.toLowerCase()}
            id={
              "round-north" + (positions.NORTH == currentIndex ? "-filled" : "")
            }
          >
            {getSpanForSymbol(round[3].first)}
            <span>{round[3].second}</span>
          </div>
        </div>
        <div className="row gap-20">
          <div
            className={"col flex justify-center items-center " + round[2].first.toLowerCase()}
            id={
              "round-west" + (positions.WEST == currentIndex ? "-filled" : "")
            }
          >
            {getSpanForSymbol(round[2].first)}
            <span>{round[2].second}</span>
          </div>
          <div
            className={"col flex justify-center items-center " + round[0].first.toLowerCase()}
            id={
              "round-east" + (positions.EAST == currentIndex ? "-filled" : "")
            }
          >
            {getSpanForSymbol(round[0].first)}
            <span>{round[0].second}</span>
          </div>
        </div>
        <div
          className={"row"}
        >
          <div
            className={"col flex justify-center items-center flex-grow-0 " + round[1].first.toLowerCase()}
            id={
              "round-south" + (positions.SOUTH == currentIndex ? "-filled" : "")
            }
          >
            {getSpanForSymbol(round[1].first)}
            <span>{round[1].second}</span>
          </div>
        </div>
      </section>
    </>
  );
}

export default CurrentRound;
