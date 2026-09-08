import { useState } from 'react'
import Swal from 'sweetalert2'

function ComplaintForm() {
  const [description, setDescription] = useState('')
  const [wasteType, setWasteType] = useState('MIXED_WASTE')
  const [priority, setPriority] = useState('MEDIUM')

  async function handleSubmit(event) {
    event.preventDefault()

    const token = localStorage.getItem('jwtToken')

    try {
      const response = await fetch('http://localhost:8080/complaints', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({
          description,
          wasteType,
          priority,
        }),
      })

      const data = await response.json()

      if (response.ok) {
        Swal.fire({
          icon: 'success',
          title: 'Complaint Submitted!',
          text: `Your complaint number is ${data.data.complaintNumber}`,
          confirmButtonText: 'OK',
        })

        setDescription('')
        setWasteType('MIXED_WASTE')
        setPriority('MEDIUM')
      } else {
        Swal.fire({
          icon: 'error',
          title: 'Submission Failed',
          text: data.message || 'Unable to submit complaint.',
          confirmButtonText: 'OK',
        })
      }
    } catch (error) {
      console.error('Error creating complaint:', error)

      Swal.fire({
        icon: 'error',
        title: 'Something went wrong',
        text: 'Unable to connect to the server.',
        confirmButtonText: 'OK',
      })
    }
  }

  return (
    <section className="complaint-card">
      <p className="eyebrow">REPORT WASTE</p>

      <h2>Report a Waste Problem</h2>

      <p className="complaint-subtitle">
        Tell us about the waste issue and we will take care of it.
      </p>

      <form onSubmit={handleSubmit}>
        <label>
          Description

          <textarea
            value={description}
            onChange={(event) => setDescription(event.target.value)}
            placeholder="Describe the waste problem..."
            rows="5"
            required
          />
        </label>

        <label>
          Waste Type

          <select
            value={wasteType}
            onChange={(event) => setWasteType(event.target.value)}
          >
            <option value="MIXED_WASTE">Mixed Waste</option>
            <option value="ORGANIC_WASTE">Organic Waste</option>
            <option value="PLASTIC_WASTE">Plastic Waste</option>
            <option value="PAPER_WASTE">Paper Waste</option>
            <option value="GLASS_WASTE">Glass Waste</option>
            <option value="ELECTRONIC_WASTE">Electronic Waste</option>
            <option value="CONSTRUCTION_WASTE">Construction Waste</option>
            <option value="OTHER">Other</option>
          </select>
        </label>

        <label>
          Priority

          <select
            value={priority}
            onChange={(event) => setPriority(event.target.value)}
          >
            <option value="LOW">Low</option>
            <option value="MEDIUM">Medium</option>
            <option value="HIGH">High</option>
            <option value="CRITICAL">Critical</option>
          </select>
        </label>

        <button
          className="primary-button complaint-button"
          type="submit"
        >
          Submit Complaint
        </button>
      </form>
      
    </section>
  )
}

export default ComplaintForm