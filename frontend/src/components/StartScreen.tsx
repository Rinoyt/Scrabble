import React from "react";

interface StartScreenProps {
  onStart: () => void;
}

const StartScreen: React.FC<StartScreenProps> = ({ onStart }) => {
  return (
    <div className="h-screen flex flex-col items-center justify-center">
      <h1 className="text-3xl font-bold mb-10">Scrabble Online</h1>
      <button
        onClick={onStart}
        className="px-6 py-3 bg-green-600 text-white text-lg shadow rounded cursor-pointer"
      >
        Начать игру
      </button>
    </div>
  );
};

export default StartScreen;
