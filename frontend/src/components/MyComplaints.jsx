import { useEffect, useState } from 'react'

function MyComplaints() {
  const [complaints, setComplaints] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  useEffect(() => {
    fetchMyComplaints()
  }, [])

  async function fetchMyComplaints() {
    const token = localStorage.getItem('jwtToken')

    try {
      const response = await fetch('http://localhost:8080/complaints/my', {
        method: 'GET',
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })

      const data = await response.json()

      if (response.ok) {
        setComplaints(data.data || [])
      } else {
        setError(data.message || 'Unable to fetch complaints.')
      }
    } catch (error) {
      console.error('Error fetching complaints:', error)
      setError('Unable to connect to the server.')
    } finally {
      setLoading(false)
    }
  }

  function formatText(value) {
    return value ? value.replaceAll('_', ' ') : ''
  }

  if (loading) {
    return (
      <section className="my-complaints">
        <p className="eyebrow">MY COMPLAINTS</p>
        <h2>Loading complaints...</h2>
      </section>
    )
  }

  if (error) {
    return (
      <section className="my-complaints">
        <p className="eyebrow">MY COMPLAINTS</p>
        <h2>Unable to load complaints</h2>
        <p className="complaint-error">{error}</p>
      </section>
    )
  }

  if (complaints.length === 0) {
    return (
      <section className="my-complaints">
        <p className="eyebrow">MY COMPLAINTS</p>
        <h2>No complaints yet</h2>
        <p className="complaint-subtitle">
          Your submitted complaints will appear here.
        </p>
      </section>
    )
  }

  return (
    <section className="my-complaints">
      <div className="my-complaints-header">
        <div>
          <p className="eyebrow">MY COMPLAINTS</p>
          <h2>Your Waste Complaints</h2>
        </div>

        <span className="complaint-count">
          {complaints.length} Complaints
        </span>
      </div>

      <div className="complaints-list">
        {complaints.map((complaint) => (
          <article className="complaint-item" key={complaint.id}>
            <div className="complaint-item-header">
              <div>
                <p className="complaint-number">
                  {complaint.complaintNumber}
                </p>

                <h3>{complaint.description}</h3>
              </div>

              <span
                className={`status-badge status-${complaint.complaintStatus.toLowerCase()}`}
              >
                {formatText(complaint.complaintStatus)}
              </span>
            </div>

            <div className="complaint-details">
              <div>
                <span>Waste Type</span>
                <strong>{formatText(complaint.wasteType)}</strong>
              </div>

              <div>
                <span>Priority</span>
                <strong
                  className={`priority-badge priority-${complaint.priority.toLowerCase()}`}
                >
                  {formatText(complaint.priority)}
                </strong>
              </div>

              <div>
                <span>Assignment</span>
                <strong>
                  {formatText(complaint.assignmentStatus)}
                </strong>
              </div>

              <div>
                <span>Submitted</span>
                <strong>
                  {new Date(complaint.createdAt).toLocaleDateString()}
                </strong>
              </div>
            </div>
          </article>
        ))}
      </div>
    </section>
  )
}

export default MyComplaints