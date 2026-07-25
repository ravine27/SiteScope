import React from 'react';
import { Loader2 } from 'lucide-react';

export default function LoadingSpinner({ url }) {
  return (
    <div className="glass-card state-container" style={{ margin: '2rem auto', maxWidth: '600px' }}>
      <Loader2 size={48} className="spinner" style={{ color: 'var(--brand-primary)' }} />
      <h2 style={{ fontSize: '1.5rem', fontWeight: 700, marginBottom: '0.5rem' }}>Analyzing Website...</h2>
      <p style={{ color: 'var(--text-secondary)', fontSize: '0.95rem' }}>
        Fetching metrics and scanning SEO, accessibility, and performance data for:
      </p>
      <div style={{ marginTop: '0.75rem', padding: '0.5rem 1rem', background: 'var(--bg-tertiary)', borderRadius: 'var(--radius-md)', fontFamily: 'monospace', color: 'var(--brand-primary)', wordBreak: 'break-all' }}>
        {url}
      </div>
    </div>
  );
}
