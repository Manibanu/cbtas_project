import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { api } from '../api.js'
import { getStudent } from '../App.jsx'

export default function History() {
  const navigate = useNavigate()
  const student = getStudent()
  const [results, setResults] = useState([])
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    async function load() {
      try {
        const data = await api.getResultsForStudent(student.id)
        setResults(data)
      } catch (err) {
        setError(err.message)
      } finally {
        setLoading(false)
      }
    }
    load()
  }, [student.id])

  return (
    <div className="page-wide">
      <div className="topbar">
        <div className="who">
          logged in as <span>{student?.name}</span>
        </div>
        <div className="topbar-actions">
          <button onClick={() => navigate('/dashboard')}>new test</button>
        </div>
      </div>

      <div className="page" style={{ padding: 0 }}>
        <div className="term term-wide">
          <div className="term-bar">
            <span className="term-dot red" />
            <span className="term-dot yellow" />
            <span className="term-dot green" />
            <span className="term-title">cbtas — history</span>
          </div>
          <div className="term-body">
            <div className="prompt">past-attempts</div>
            <h1>Your test history</h1>
            <p className="subtitle">Every practice test you've completed.</p>

            {error && <div className="error-box">{error}</div>}
            {loading && <p className="subtitle">Loading…</p>}

            {!loading && results.length === 0 && (
              <div className="empty-state">
                No tests taken yet. Start one from the dashboard.
              </div>
            )}

            {results.map((r) => (
              <div className="history-item" key={r.id}>
                <div>
                  <strong>{r.company?.companyName}</strong> · {r.technology?.technologyName}
                  <div style={{ color: 'var(--muted)', fontSize: 12, marginTop: 2 }}>
                    {r.correctAnswers}/{r.totalQuestions} correct · {r.percentage?.toFixed(1)}%
                  </div>
                </div>
                <span className={`badge ${r.resultStatus === 'PASS' ? 'pass' : 'fail'}`}>
                  {r.resultStatus}
                </span>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  )
}