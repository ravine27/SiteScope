import React from 'react';
import { AlertCircle, RefreshCw } from 'lucide-react';

export default function ErrorAlert({ message, onRetry }) {
  return (
    <div className="alert-error">
      <AlertCircle size={24} style={{ flexShrink: 0 }} />
      <div style={{ flex: 1, textAlign: 'left' }}>
        <h4 style={{ fontWeight: 700, marginBottom: '0.25rem' }}>Audit Failed</h4>
        <p style={{ fontSize: '0.9rem', opacity: 0.95 }}>{message}</p>
      </div>
      {onRetry && (
        <button 
          onClick={onRetry} 
          style={{ background: 'white', color: 'var(--error)', border: 'none', padding: '0.5rem 0.85rem', borderRadius: 'var(--radius-sm)', fontWeight: 600, cursor: 'pointer', display: 'flex', alignItems: 'center', gap: '0.4rem', fontSize: '0.85rem' }}
        >
          <RefreshCw size={14} />
          Retry
        </button>
      )}
    </div>
  );
}
