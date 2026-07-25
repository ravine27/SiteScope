import React, { useState } from 'react';
import { Globe, ArrowRight } from 'lucide-react';

export default function SearchBar({ onAnalyze, isLoading }) {
  const [url, setUrl] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    if (url.trim()) {
      onAnalyze(url.trim());
    }
  };

  return (
    <div className="search-box-container">
      <form onSubmit={handleSubmit} className="search-form">
        <div className="search-input-wrapper">
          <Globe size={20} />
          <input
            type="text"
            className="search-input"
            placeholder="Enter public website URL (e.g. https://example.com)"
            value={url}
            onChange={(e) => setUrl(e.target.value)}
            disabled={isLoading}
            required
          />
        </div>
        <button type="submit" className="btn-primary" disabled={isLoading || !url.trim()}>
          <span>{isLoading ? 'Analyzing...' : 'Analyze Website'}</span>
          <ArrowRight size={18} />
        </button>
      </form>
    </div>
  );
}
