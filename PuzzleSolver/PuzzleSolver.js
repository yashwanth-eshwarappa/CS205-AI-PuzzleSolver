import Heap from "heap-js";

class node {
    puzzle = [];
    up = null;
    down = null;
    left = null;
    right = null;
    constructor(puzzle) {
        this.puzzle = puzzle;
        this.up = this.down = this.right = this.left = null;
        this.g = 0;
        this.h = 0;
        this.f = 0;
    }

    genericSearch(algoSelected) {
        var compareNumbers = function (a, b) { return a - b; };
        var pq = new Heap({ comparator: compareNumbers });
        console.log("inside genericSearch()");
    }

}

var puzzleMainNode = null;

function createPuzzle() {
    def = document.getElementById("puzzleSelectorDefault");
    if (def.checked) {
        console.log("default")
        puzzleMainNode = new node([[1, 2, 0], [4, 5, 3], [7, 8, 6]]);
        console.log(puzzleMainNode);
        document.getElementById("inputCustom").hidden = true;
        document.getElementById("algoSelect").hidden = false;
        // selectAlgorithm(puzzleMainNode);
    }
    else {
        console.log("Custom")
        document.getElementById("initialInput").hidden = true;
        document.getElementById("inputCustom").hidden = false;
    }
}
function getCustomPuzzle() {
    var textArea = document.getElementById("inputCustomText");
    var arrayOfLines = textArea.value.split("\n");
    puzzle = []
    for (var i = 0; i < 3; i++) {
        let each = arrayOfLines[i].split(" ");
        if (each.length !== 3) {
            alert("Please enter proper input!");
            document.getElementById("inputCustomText").value = "";
            break;
        }
        puzzle.push(each);
    };
    puzzleMainNode = new node(puzzle);
    document.getElementById("inputCustom").hidden = true;
    document.getElementById("algoSelect").hidden = false;
    // selectAlgorithm(puzzleMainNode);
}


function algoSelectSubmit() {
    let uniformSelect = document.getElementById("uniform");
    let misplaced = document.getElementById("misplaced");

    if (uniformSelect.checked) {
        puzzleMainNode.genericSearch(1);
    }
    else if (misplaced.checked) {
        puzzleMainNode.genericSearch(2);
    }
    else {
        puzzleMainNode.genericSearch(3);
    }

}