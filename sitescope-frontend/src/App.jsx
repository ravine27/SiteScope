import React, { useState, useEffect } from 'react';
import Navbar from './components/Navbar';
import SearchBar from './components/SearchBar';
import ScoreCard from './components/ScoreCard';
import MetricCard from './components/MetricCard';
import SuggestionCard from './components/SuggestionCard';
import LoadingSpinner from './components/LoadingSpinner';
import ErrorAlert from './components/ErrorAlert';
import Footer from './components/Footer';
import { auditWebsite } from './services/api';
import { Zap, ShieldCheck, Search, FileText, Image, AlignLeft, Clock, Activity, ArrowLeft } from 'lucide-react';

export default function App() {
  const [theme, setTheme] = useState('dark');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [auditData, setAuditData] = useState(null);
  const [currentUrl, setCurrentUrl] = useState('');

  useEffect(() => {
    document.documentElement.setAttribute('data-theme', theme);
  }, [theme]);

  const toggleTheme = () => {
    setTheme(prev => (prev === 'dark' ? 'light' : 'dark'));
  };

  const handleAnalyze = async (url) => {
    setLoading(true);
    setError(null);
    setCurrentUrl(url);
    try {
      const data = await auditWebsite(url);
      setAuditData(data);
    } catch (err) {
      setError(err.message || 'Failed to complete website audit.');
    } finally {
      setLoading(false);
    }
  };

  const handleReset = () => {
    setAuditData(null);
    setError(null);
    setCurrentUrl('');
  };

  return (
    <div className="app-layout">
      <Navbar theme={theme} toggleTheme={toggleTheme} />

      <main className="main-content">
        {!auditData && !loading && (
          <section className="hero-section">
            <h1 className="hero-title">Analyze. Understand. Improve.</h1>
            <p className="hero-subtitle">
              Instantly evaluate the technical health, SEO readiness, accessibility, and speed performance of any public website.
            </p>
            <SearchBar onAnalyze={handleAnalyze} isLoading={loading} />
          </section>
        )}

        {loading && <LoadingSpinner url={currentUrl} />}

        {error && (
          <div style={{ marginTop: '2rem' }}>
            <ErrorAlert message={error} onRetry={() => handleAnalyze(currentUrl)} />
            <div style={{ textCenter: 'center', textAlign: 'center' }}>
              <SearchBar onAnalyze={handleAnalyze} isLoading={loading} />
            </div>
          </div>
        )}

        {auditData && !loading && (
          <div style={{ animation: 'fadeIn 0.4s ease-out' }}>
            <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', marginBottom: '1.5rem', flexWrap: 'wrap', gap: '1rem' }}>
              <div>
                <span style={{ fontSize: '0.85rem', color: 'var(--text-muted)', fontWeight: 600, textTransform: 'uppercase' }}>Target Website</span>
                <h2 style={{ fontSize: '1.5rem', fontWeight: 800, color: 'var(--text-primary)', wordBreak: 'break-all' }}>{auditData.url}</h2>
              </div>

              <button onClick={handleReset} className="btn-primary" style={{ background: 'var(--bg-tertiary)', color: 'var(--text-primary)' }}>
                <ArrowLeft size={16} />
                <span>Analyze Another URL</span>
              </button>
            </div>

            <div className="dashboard-grid">
              {/* Overall Health Score Card */}
              <ScoreCard score={auditData.healthScore} status={auditData.healthStatus} />

              {/* Response Time Card */}
              <MetricCard
                title="Response Time"
                icon={<Clock size={20} />}
                value={`${auditData.responseTime} ms`}
                description={auditData.responseTime < 1000 ? '⚡ Ultra fast response' : auditData.responseTime < 2000 ? '👍 Good page speed' : '⚠️ Slow response time'}
                className="col-span-4"
              />

              {/* Connectivity Status Card */}
              <MetricCard
                title="HTTP Status"
                icon={<Activity size={20} />}
                value={auditData.status}
                description={auditData.status === 200 ? '✅ 200 OK — Website Accessible' : `Status Code ${auditData.status}`}
                className="col-span-4"
              />

              {/* SEO Title Card */}
              <MetricCard
                title="Page Title"
                icon={<FileText size={20} />}
                value={auditData.title || '<Missing Title>'}
                description={auditData.title ? `Length: ${auditData.title.length} characters` : '⚠️ No title tag found'}
                className="col-span-6"
              />

              {/* Meta Description Card */}
              <MetricCard
                title="Meta Description"
                icon={<Search size={20} />}
                value={auditData.metaDescription || '<Missing Description>'}
                description={auditData.metaDescription ? `Length: ${auditData.metaDescription.length} characters` : '⚠️ Meta description missing'}
                className="col-span-6"
              />

              {/* H1 Heading Count */}
              <MetricCard
                title="H1 Headings"
                icon={<AlignLeft size={20} />}
                value={`${auditData.h1Count} tag(s)`}
                description={auditData.h1Count === 1 ? 'Optimal H1 tag structure' : auditData.h1Count === 0 ? '⚠️ No H1 tag found' : 'Multiple H1 tags found'}
                className="col-span-4"
              />

              {/* Images Missing ALT */}
              <MetricCard
                title="Accessibility (Missing ALT)"
                icon={<Image size={20} />}
                value={`${auditData.imagesMissingAlt} image(s)`}
                description={auditData.imagesMissingAlt === 0 ? 'All images have ALT text' : '⚠️ Images need accessibility ALT attributes'}
                className="col-span-4"
              />

              {/* Body Word Count */}
              <MetricCard
                title="Body Word Count"
                icon={<FileText size={20} />}
                value={`${auditData.wordCount} words`}
                description={auditData.wordCount > 300 ? 'Good content depth' : 'Thin body content (<300 words)'}
                className="col-span-4"
              />

              {/* Recommendations */}
              <SuggestionCard recommendations={auditData.recommendations} />
            </div>
          </div>
        )}

        {!auditData && !loading && (
          <div className="features-grid">
            <div className="glass-card feature-card">
              <div className="metric-icon" style={{ marginBottom: '1rem', color: 'var(--brand-primary)' }}>
                <Search size={22} />
              </div>
              <h3 style={{ fontSize: '1.1rem', fontWeight: 700, marginBottom: '0.5rem' }}>SEO Analysis</h3>
              <p style={{ fontSize: '0.9rem', color: 'var(--text-secondary)' }}>
                Evaluates title tags, meta descriptions, and header hierarchy for search engine visibility.
              </p>
            </div>

            <div className="glass-card feature-card">
              <div className="metric-icon" style={{ marginBottom: '1rem', color: 'var(--brand-secondary)' }}>
                <ShieldCheck size={22} />
              </div>
              <h3 style={{ fontSize: '1.1rem', fontWeight: 700, marginBottom: '0.5rem' }}>Accessibility Check</h3>
              <p style={{ fontSize: '0.9rem', color: 'var(--text-secondary)' }}>
                Audits image alternative text (ALT attributes) ensuring WCAG accessibility compliance.
              </p>
            </div>

            <div className="glass-card feature-card">
              <div className="metric-icon" style={{ marginBottom: '1rem', color: 'var(--warning)' }}>
                <Zap size={22} />
              </div>
              <h3 style={{ fontSize: '1.1rem', fontWeight: 700, marginBottom: '0.5rem' }}>Health Scoring</h3>
              <p style={{ fontSize: '0.9rem', color: 'var(--text-secondary)' }}>
                Calculates an algorithmic health score (0-100) based on connectivity, content, and structure.
              </p>
            </div>
          </div>
        )}
      </main>

      <Footer />
    </div>
  );
}
