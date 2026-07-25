import React from 'react';
import { Activity, Sun, Moon, Github } from 'lucide-react';

export default function Navbar({ theme, toggleTheme }) {
  return (
    <header className="navbar">
      <div className="navbar-container">
        <a href="/" className="brand-logo">
          <div className="brand-icon">
            <Activity size={20} />
          </div>
          <span>SiteScope</span>
        </a>

        <div className="nav-actions">
          <button 
            className="icon-btn" 
            onClick={toggleTheme} 
            title={`Switch to ${theme === 'dark' ? 'Light' : 'Dark'} Mode`}
            aria-label="Toggle theme"
          >
            {theme === 'dark' ? <Sun size={18} /> : <Moon size={18} />}
          </button>
          
          <a 
            href="https://github.com/ravine27/SiteScope" 
            target="_blank" 
            rel="noopener noreferrer" 
            className="icon-btn"
            title="GitHub Repository"
            aria-label="GitHub Repo"
          >
            <Github size={18} />
          </a>
        </div>
      </div>
    </header>
  );
}
