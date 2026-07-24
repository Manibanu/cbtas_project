import { useEffect, useState } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { api } from '../api.js'

export default function ResultPage() {
  const { resultId } = useParams()
  const navigate = useNavigate()
  const [result, setResult] = useState(null)
  const [error, setError] = useState('')

  useEffect(() => {
    async function load() {
      try {
        const data = await api.getResultById(resultId)
        setResult(data)
      } catch (err) {
        setError(err.message)
      }
    }
    load()
  }, [resultId])

  if (error) {
    return (
      <div className="page">
        <div className="term">
          <div className="term-body">
            <div className="error-box">{error}</div>
            <button className="btn btn-ghost" onClick={() => navigate('/dashboard')}>Back to dashboard</button>
          </div>
        </div>
      </div>
    )
  }

  if (!result) {
    return (
      <div className="page">
        <div className="term">
          <div className="term-body"><p className="subtitle">Loading result…</p></div>
        </div>
      </div>
    )
  }

  const passed = result.resultStatus === 'PASS'

  return (
    <div className="page">
      <div className="term">
        <div className="term-bar">
          <span className="term-dot red" />
          <span className="term-dot yellow" />
          <span className="term-dot green" />
          <span className="term-title">cbtas — result</span>
        </div>
        <div className="term-body">
          <div className="prompt">score</div>
          <div className={`result-status ${passed ? 'pass' : 'fail'}`}>{result.resultStatus}</div>
          <p className="subtitle">
            {result.company?.companyName} · {result.technology?.technologyName} · {result.percentage?.toFixed(1)}%
          </p>

          <div className="result-grid">
            <div className="result-stat">
              <div className="num">{result.totalQuestions}</div>
              <div className="label">total</div>
            </div>
            <div className="result-stat">
              <div className="num" style={{ color: 'var(--success)' }}>{result.correctAnswers}</div>
              <div className="label">correct</div>
            </div>
            <div className="result-stat">
              <div className="num" style={{ color: 'var(--danger)' }}>{result.wrongAnswers}</div>
              <div className="label">wrong</div>
            </div>
          </div>

          <div className="nav-row">
            <button className="btn btn-ghost" onClick={() => navigate('/history')}>view history</button>
            <button className="btn btn-primary" onClick={() => navigate('/dashboard')}>take another test</button>
          </div>
        </div>
      </div>
    </div>
  )
}