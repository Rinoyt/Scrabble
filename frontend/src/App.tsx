import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Board from "./components/Board";

const App: React.FC = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Board />} />
      </Routes>
    </Router>
  );
};

export default App;