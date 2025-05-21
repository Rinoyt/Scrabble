import React, { useState } from "react";
import { v4 as uuidv4 } from "uuid";
import StartScreen from "./StartScreen";

const letterPoints: Record<string, number> = {
    A: 1, B: 3, C: 3, D: 2, E: 1, F: 4, G: 2,
    H: 4, I: 1, J: 8, K: 5, L: 1, M: 3, N: 1,
    O: 1, P: 3, Q: 10, R: 1, S: 1, T: 1, U: 1,
    V: 4, W: 4, X: 8, Y: 4, Z: 10
};


const generateRack = (): string[] => {
    const letters = Object.keys(letterPoints);
    return Array.from({ length: 7 }, () => letters[Math.floor(Math.random() * letters.length)]);
};

const Board: React.FC = () => {
    const [board, setBoard] = useState<(string | null)[][]>(Array(15).fill(null).map(() => Array(15).fill(null)));
    const [rack, setRack] = useState<string[]>(generateRack());
    const [score, setScore] = useState<number>(0);
    const [gameStarted, setGameStarted] = useState<boolean>(false);

    const [draggedLetter, setDraggedLetter] = useState<string | null>(null);
    const [draggedIndex, setDraggedIndex] = useState<number | null>(null);


    const onDragStart = (letter: string, index: number) => {
        setDraggedLetter(letter);
        setDraggedIndex(index);
    };

    const onDrop = (row: number, col: number) => {
        if (!draggedLetter || draggedIndex === null) return;

        const newBoard = board.map((row) => [...row]);
        if (newBoard[row][col]) return; // cell is occupied

        newBoard[row][col] = draggedLetter;
        setBoard(newBoard);

        const newRack = [...rack];
        newRack.splice(draggedIndex, 1);

        setRack(newRack);
        setDraggedLetter(null);
        setDraggedIndex(null);
    };

    const onReturnToRack = (row: number, col: number) => {
        const newBoard = board.map((row) => [...row]);
        const letter = newBoard[row][col];
        if (!letter) return;

        newBoard[row][col] = null;
        setBoard(newBoard);
        setRack([...rack, letter]);
    };

    const makeMove = () => { };

    const skipMove = () => { };

    const retakeTiles = () => { };

    const startGame = () => {
        // Здесь будет http-запрос
        setGameStarted(true);
    };

    if (!gameStarted) {
        return <StartScreen onStart={startGame} />;
    }

    return (
        <div className="p-4 flex flex-col items-center">
            <h1 className="text-2xl font-bold mb-4">Scrabble Online</h1>
            <div className="mb-4">Score: {score}</div>
            {/* <button onClick={calculateScore} className="mb-4 px-4 py-2 bg-blue-500 text-white rounded">Подсчитать очки</button> */}

            <div className="grid grid-cols-1 mb-1">
                {board.map((row, rowIndex) => (
                    <div key={uuidv4()} className="flex">
                        {row.map((cell, colIndex) => (
                            <div
                                key={uuidv4()}
                                onDragOver={(e) => e.preventDefault()}
                                onDrop={() => onDrop(rowIndex, colIndex)}
                                onDoubleClick={() => onReturnToRack(rowIndex, colIndex)}
                                className="w-8 h-8 border border-gray-400 flex items-center justify-center text-lg bg-green-100 cursor-pointer text-gray-950"
                            >
                                {cell}
                            </div>
                        ))}
                    </div>
                ))}
            </div>

            <p className="mt-2 mb-4 text-xs text-gray-600">* Двойной клик по букве на поле — вернуть её обратно в руку</p>
            <div className="flex space-x-2 mb-4">
                {rack.map((letter, idx) => (
                    <div
                        key={idx}
                        draggable
                        onDragStart={() => onDragStart(letter, idx)}
                        className="w-10 h-10 bg-emerald-600 border rounded-xs flex items-center justify-center cursor-move shadow"
                    >
                        {letter}
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
