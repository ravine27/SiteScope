import React from 'react';

export default function MetricCard({ title, icon, value, description, className = 'col-span-4' }) {
  return (
    <div className={`glass-card metric-card ${className}`}>
      <div className="metric-header">
        <span className="metric-title">{title}</span>
        <div className="metric-icon">{icon}</div>
      </div>
      <div className="metric-value">{value}</div>
      {description && <div className="metric-desc">{description}</div>}
    </div>
  );
}
