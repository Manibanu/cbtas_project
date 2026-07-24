import { useEffect, useState } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { api } from '../api.js'

const OPTION_KEYS = ['A', 'B', 'C', 'D']

export default function TestPage() {
  const { testId } = useParams()
  const navigate = useNavigate()

  const [questions, setQuestions] = useState([])
  const [index, setIndex] = useState(0)
  const [answers, setAnswers] = useState({})
  const [loading, setLoading] = useState(true)
  const [submitting, setSubmitting] = useState(false)
  const [error, setError] = useState('')

  useEffect(() => {
    async function load() {
      try {
        const data = await api.getTestQuestions(testId)
        setQuestions(data)
      } catch (err) {
        setError(err.message)
      } finally {
        setLoading(false)
      }
    }
    load()
  }, [testId])

  function selectAnswer(questionId, value) {
    setAnswers((a) => ({ ...a, [questionId]: value }))
  }

  async function handleSubmit() {
    setSubmitting(true)
    setError('')
    try {
      const payload = questions.map((q) => ({
        questionId: q.questionId,
        selectedAnswer: answers[q.questionId] || '',
      }))
      const result = await api.submitTest(testId, payload)
      navigate(`/result/${result.id}`)
    } catch (err) {
      setError(err.message)
      setSubmitting(false)
    }
  }

  if (loading) {
    return (
      <div className="page">
        <div className="term">
          <div className="term-body">
            <p className="subtitle">Loading questions…</p>
          </div>
        </div>
      </div>
    )
  }

  if (error && questions.length === 0) {
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

  const q = questions[index]
  const isLast = index === questions.length - 1
  const answeredCount = Object.keys(answers).length

  return (
    <div className="page">
      <div className="term term-wide">
        <div className="term-bar">
          <span className="term-dot red" />
          <span className="term-dot yellow" />
          <span className="term-dot green" />
          <span className="term-title">cbtas — test #{testId}</span>
        </div>
        <div className="term-body">
          <div className="q-progress">
            question {index + 1} / {questions.length} · {answeredCount} answered
          </div>
          <div className="q-progress-bar">
            <div className="q-progress-fill" style={{ width: `${((index + 1) / questions.length) * 100}%` }} />
          </div>

          {error && <div className="error-box">{error}</div>}

          <div className="q-text">{q.question}</div>

          <div className="options">
            {OPTION_KEYS.map((key) => {
              const optionText = q[`option${key}`]
              const selected = answers[q.questionId] === optionText
              return (
                <div
                  key={key}
                  className={`option${selected ? ' selected' : ''}`}
                  onClick={() => selectAnswer(q.questionId, optionText)}
                >
                  <span className="key">{key}</span>
                  <span>{optionText}</span>
                </div>
              )
            })}
          </div>

          <div className="nav-row">
            <button
              className="btn btn-ghost"
              disabled={index === 0}
              onClick={() => setIndex((i) => i - 1)}
            >
              ← previous
            </button>

            {isLast ? (
              <button className="btn btn-primary" disabled={submitting} onClick={handleSubmit}>
                {submitting ? 'submitting…' : 'Submit test'}
              </button>
            ) : (
              <button className="btn btn-primary" onClick={() => setIndex((i) => i + 1)}>
                next →
              </button>
            )}
          </div>
        </div>
      </div>
    </div>
  )
}