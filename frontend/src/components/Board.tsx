import React, { useState } from "react";
import { v4 as uuidv4 } from "uuid";
import StartScreen from "./StartScreen";

import clsx from "clsx";

interface Multiplier {
    type: "BLANK" | "WORD" | "LETTER",
    m: number,
}

interface GameData {
    bag: object,
    board: BoardData,
    currentTurnPlayerId: number,
    id: number,
    opponents: object[],
    player: PlayerData,
    status: string,
    turn: number
}

interface PlayerData {
    id: number,
    name: string,
    score: number,
    hand: HandData
}

interface HandData {
    tiles: TileData[]
}

interface TileData {
    id: number,
    character: string,
    score: number,
    blank: boolean
}

interface BoardData {
    board: CellData[][]
}

interface CellData {
    type: "BLANK" | "WORD" | "LETTER",
    multiplier: number,
    tile: object | null
}

const LETTER2_BG: string = "bg-blue-300";
const LETTER3_BG: string = "bg-red-300";
const WORD2_BG: string = "bg-yellow-300";
const WORD3_BG: string = "bg-pink-300";
const BLANK_BG: string = "bg-green-100";

const getCellColor = (multiplier: Multiplier | null): string => {
    if (multiplier === null) {
        return LETTER2_BG
    }

    if (multiplier.type == "LETTER" && multiplier.m == 2) {
        return LETTER2_BG
    }
    else if (multiplier.type == "LETTER" && multiplier.m == 3) {
        return LETTER3_BG
    }
    else if (multiplier.type == "WORD" && multiplier.m == 2) {
        return WORD2_BG
    }
    else if (multiplier.type == "WORD" && multiplier.m == 3) {
        return WORD3_BG
    }
    else {
        return BLANK_BG
    }
}

const Board: React.FC = () => {
    const [board, setBoard] = useState<(TileData | null)[][]>(Array(15).fill(null).map(() => Array(15).fill(null)));
    const [multipliers, setMultipliers] = useState<(Multiplier | null)[][]>(Array(15).fill(null).map(() => Array(15).fill(null)));

    const [hand, setHand] = useState<(TileData | null)[]>([]);
    const [score, setScore] = useState<number>(0);
    const [gameStarted, setGameStarted] = useState<boolean>(false);

    const [draggedLetter, setDraggedLetter] = useState<TileData | null>(null);
    const [draggedIndex, setDraggedIndex] = useState<number | null>(null);


    const onDragStart = (tile: TileData | null, index: number) => {
        setDraggedLetter(tile);
        setDraggedIndex(index);
    };

    const onDrop = (row: number, col: number) => {
        if (!draggedLetter || draggedIndex === null) return;

        const newBoard = board.map((row) => [...row]);
        if (newBoard[row][col]) return; // cell is occupied

        newBoard[row][col] = draggedLetter;
        setBoard(newBoard);

        const newHand = [...hand];
        newHand.splice(draggedIndex, 1);

        setHand(newHand);
        setDraggedLetter(null);
        setDraggedIndex(null);
    };

    const onReturnToRack = (row: number, col: number) => {
        const newBoard = board.map((row) => [...row]);
        const tile = newBoard[row][col];
        if (!tile) return;

        newBoard[row][col] = null;
        setBoard(newBoard);
        setHand([...hand, tile]);
    };

    const makeMove = () => { };

    const skipMove = () => { };

    const retakeTiles = () => { };

    const startGame = async () => {
        setGameStarted(true);

        try {
            const response = await fetch("http://localhost:8090/api/1/game", { method: "GET" });
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const data: GameData = await response.json();
            console.log("Server response:", data);

            const ms: (Multiplier | null)[][] = data.board.board.map((row) => row.map((cell) => ({ type: cell.type, m: cell.multiplier })));
            setMultipliers(ms);

            const hand: (TileData | null)[] = data.player.hand.tiles;
            setHand(hand);

            setScore(data.player.score);
            setGameStarted(true);
        } catch (error) {
            console.error("Error while starting a game:", error);
        }
    };

    if (!gameStarted) {
        return <StartScreen onStart={startGame} />;
    }

    return (
        <div className="p-4 flex flex-col items-center">
            <h1 className="text-2xl font-bold mb-4">Scrabble Online</h1>
            <div className="mb-2">Score: {score}</div>
            <div className="mb-3 flex flex-row gap-4">
                <div className="flex flex-row items-center gap-1.5">
                    <div className={clsx("w-4 h-4 border border-gray-400", LETTER2_BG)} />
                    <span>Буква х2</span>
                </div>

                <div className="flex flex-row items-center gap-2">
                    <div className={clsx("w-4 h-4 border border-gray-400", LETTER3_BG)} />
                    <span>Буква х3</span>
                </div>

                <div className="flex flex-row items-center gap-2">
                    <div className={clsx("w-4 h-4 border border-gray-400", WORD2_BG)} />
                    <span>Слово х2</span>
                </div>

                <div className="flex flex-row items-center gap-2">
                    <div className={clsx("w-4 h-4 border border-gray-400", WORD3_BG)} />
                    <span>Слово х3</span>
                </div>
            </div>

            <div className="grid grid-cols-1 mb-1">
                {board.map((row, rowIndex) => (
                    <div key={uuidv4()} className="flex">
                        {row.map((tile, colIndex) => (
                            <div
                                key={uuidv4()}
                                onDragOver={(e) => e.preventDefault()}
                                onDrop={() => onDrop(rowIndex, colIndex)}
                                onDoubleClick={() => onReturnToRack(rowIndex, colIndex)}
                                className={clsx(
                                    "w-8 h-8 border border-gray-400 flex items-center justify-center text-lg cursor-pointer text-gray-950",
                                    getCellColor(multipliers[rowIndex][colIndex])
                                )}
                            >
                                {tile?.character}
                            </div>
                        ))}
                    </div>
                ))}
            </div>

            <p className="mt-2 mb-4 text-xs text-gray-600">* Двойной клик по букве на поле — вернуть её обратно в руку</p>
            <div className="flex space-x-2 mb-4">
                {hand.map((tile, idx) => (
                    <div
                        key={idx}
                        draggable
                        onDragStart={() => onDragStart(tile, idx)}
                        className="relative w-10 h-10 bg-emerald-600 border rounded-xs flex items-center justify-center cursor-move shadow"
                    >
                        {tile?.character}
                        <span className="absolute bottom-0.5 right-1 text-[10px] font-normal">
                            {tile?.score}
                        </span>
                    </div>
                ))}
            </div>

            <div className="flex flex-row gap-2">
                <button disabled onClick={makeMove} className="mb-4 px-4 py-2 bg-emerald-900 text-white rounded cursor-pointer disabled:bg-gray-500 disabled:cursor-auto">
                    Сделать ход
                </button>
                <button onClick={skipMove} className="mb-4 px-4 py-2 bg-emerald-900 text-white rounded cursor-pointer disabled:bg-gray-500 disabled:cursor-auto">
                    Пропустить ход
                </button>
                <button disabled onClick={retakeTiles} className="mb-4 px-4 py-2 bg-emerald-900 text-white rounded cursor-pointer disabled:bg-gray-500 disabled:cursor-auto">
                    Сменить руку
                </button>
            </div>

        </div>
    );
};

export default Board;
