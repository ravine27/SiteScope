import React from 'react';

export default function Footer() {
  return (
    <footer className="footer">
      <p>SiteScope — Website Health & SEO Analyzer &copy; {new Date().getFullYear()}</p>
      <p style={{ fontSize: '0.85rem', marginTop: '0.25rem' }}>Built with Spring Boot 3.x, Jsoup, Java 21 &amp; React | Built by Radha Agarwal</p>
    </footer>
  );
}
