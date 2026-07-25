import React from 'react';
import { Lightbulb, CheckCircle2, AlertTriangle } from 'lucide-react';

export default function SuggestionCard({ recommendations = [] }) {
  return (
    <div className="glass-card suggestions-panel col-span-12">
      <div className="section-heading">
        <Lightbulb size={22} style={{ color: 'var(--warning)' }} />
        <span>Actionable Optimization Recommendations</span>
      </div>

      {recommendations.length === 0 ? (
        <div style={{ display: 'flex', alignItems: 'center', gap: '0.75rem', padding: '1.25rem', background: 'var(--success-bg)', color: 'var(--success)', borderRadius: 'var(--radius-md)', fontWeight: 500 }}>
          <CheckCircle2 size={20} />
          <span>Great job! No critical issues were detected on this webpage.</span>
        </div>
      ) : (
        <div className="suggestion-list">
          {recommendations.map((rec, index) => (
            <div key={index} className="suggestion-item">
              <AlertTriangle className="suggestion-icon" size={18} />
              <span>{rec}</span>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
