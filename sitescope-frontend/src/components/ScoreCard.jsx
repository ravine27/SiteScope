import React from 'react';
import { Award, CheckCircle2, AlertTriangle, AlertCircle, Info } from 'lucide-react';

export default function ScoreCard({ score, status }) {
  const radius = 65;
  const circumference = 2 * Math.PI * radius;
  const strokeDashoffset = circumference - (score / 100) * circumference;

  const getStatusColor = () => {
    if (score >= 90) return 'var(--success)';
    if (score >= 70) return 'var(--info)';
    if (score >= 50) return 'var(--warning)';
    return 'var(--error)';
  };

  const getStatusBadgeClass = () => {
    if (score >= 90) return 'status-excellent';
    if (score >= 70) return 'status-good';
    if (score >= 50) return 'status-improvement';
    return 'status-poor';
  };

  const getStatusIcon = () => {
    if (score >= 90) return <CheckCircle2 size={16} />;
    if (score >= 70) return <Info size={16} />;
    if (score >= 50) return <AlertTriangle size={16} />;
    return <AlertCircle size={16} />;
  };

  return (
    <div className="glass-card score-card col-span-4">
      <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', color: 'var(--text-secondary)', fontWeight: 600 }}>
        <Award size={18} />
        <span>Overall Health Score</span>
      </div>

      <div className="score-gauge">
        <svg viewBox="0 0 160 160">
          <circle
            className="score-circle-bg"
            cx="80"
            cy="80"
            r={radius}
          />
          <circle
            className="score-circle-progress"
            cx="80"
            cy="80"
            r={radius}
            style={{
              stroke: getStatusColor(),
              strokeDasharray: circumference,
              strokeDashoffset: strokeDashoffset,
            }}
          />
        </svg>
        <div className="score-inner-text">
          <span className="score-number" style={{ color: getStatusColor() }}>{score}</span>
          <span className="score-max">/ 100</span>
        </div>
      </div>

      <div className={`status-badge ${getStatusBadgeClass()}`}>
        {getStatusIcon()}
        <span>{status}</span>
      </div>
    </div>
  );
}
