import { useState } from 'react'
import './App.css'
import LoginForm from './components/LoginForm'

function App() {
  const [showLogin, setShowLogin] = useState(false)
  const [isLoggedIn, setIsLoggedIn] = useState(
    Boolean(localStorage.getItem('jwtToken')),
  )

  function logout() {
    localStorage.removeItem('jwtToken')
    setIsLoggedIn(false)
    setShowLogin(false)
  }

  if (showLogin) {
    return (
      <LoginForm
        onLogin={() => {
          setIsLoggedIn(true)
          setShowLogin(false)
        }}
        onCancel={() => setShowLogin(false)}
      />
    )
  }

  if (isLoggedIn) {
    return (
      <main className="dashboard">
        <header className="dashboard-header">
          <p className="eyebrow">SMART WASTE MANAGEMENT</p>
          <button className="secondary-button" onClick={logout}>
            Log out
          </button>
        </header>

        <section className="dashboard-content">
          <p className="eyebrow">DASHBOARD</p>
          <h1>Welcome back.</h1>
          <p className="hero-text">
            Your account is connected securely. Waste-bin and collection data will
            appear here as we build those modules.
          </p>
        </section>
      </main>
    )
  }

  return (
    <main className="app">
      <section className="hero">
        <p className="eyebrow">SMART WASTE MANAGEMENT</p>
        <h1>Cleaner communities, smarter collection.</h1>
        <p className="hero-text">
          Monitor waste bins, manage collection activity, and keep your city cleaner
          with one simple platform.
        </p>

        <div className="hero-actions">
          <button className="primary-button" onClick={() => setShowLogin(true)}>
            Get Started
          </button>
          <button className="secondary-button">Learn More</button>
        </div>
      </section>

      <section className="feature-grid">
        <article className="feature-card">
          <span>♻</span>
          <h2>Waste Bins</h2>
          <p>Track bin details, locations, capacity, and fill levels.</p>
        </article>

        <article className="feature-card">
          <span>🚛</span>
          <h2>Collections</h2>
          <p>Plan and monitor waste-collection schedules efficiently.</p>
        </article>

        <article className="feature-card">
          <span>👤</span>
          <h2>Users</h2>
          <p>Manage citizens, workers, drivers, and administrators securely.</p>
        </article>
      </section>
    </main>
  )
}

export default App