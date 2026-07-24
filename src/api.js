 const BASE_URL = 'http://localhost:8081'

async function request(path, options = {}) {
  const response = await fetch(`${BASE_URL}${path}`, {
    headers: { 'Content-Type': 'application/json' },
    ...options,
  })

  const contentType = response.headers.get('content-type') || ''
  const isJson = contentType.includes('application/json')
  const body = isJson ? await response.json() : await response.text()

  if (!response.ok) {
    const message = isJson ? (body.message || JSON.stringify(body)) : body
    throw new Error(message || `Request failed (${response.status})`)
  }

  return body
}

export const api = {
  registerStudent: (data) =>
    request('/student/save', { method: 'POST', body: JSON.stringify(data) }),

  loginStudent: (email, password) =>
    request('/student/login', {
      method: 'POST',
      body: JSON.stringify({ email, password }),
    }),

  getCompanies: () => request('/company/all'),
  getTechnologies: () => request('/technology/all'),

  startTest: (payload) =>
    request('/test/start', { method: 'POST', body: JSON.stringify(payload) }),

  getTestQuestions: (testId) => request(`/test/${testId}/questions`),

  submitTest: (testId, answers) =>
    request(`/api/test/submit/${testId}`, {
      method: 'POST',
      body: JSON.stringify(answers),
    }),

  getResultsForStudent: (studentId) =>
    request(`/result/student/${studentId}`),

  getResultById: (resultId) => request(`/result/${resultId}`),
}