// Obtener los datos de los préstamos desde el backend
axios.get('/api/loans')
  .then(response => {
    const loanData = response.data;
    console.log('Loan data:', loanData);

    // Preparar los datos para el gráfico
    const loanTypes = loanData.map(loan => loan.name);
    const loanAmounts = loanData.map(loan => loan.maxAmount);

    console.log('Loan types:', loanTypes);
    console.log('Loan amounts:', loanAmounts);

    // Renderizar el gráfico de barras
    renderBarChart(loanTypes, loanAmounts);
  })
  .catch(error => {
    console.error('Error fetching loan data:', error);
  });

// Función para renderizar el gráfico de barras
function renderBarChart(labels, data) {
  const ctx = document.getElementById('myChart').getContext('2d');
  new Chart(ctx, {
    type: 'bar',
    data: {
      labels: labels,
      datasets: [{
        label: 'Max Loan Amount',
        data: data,
        backgroundColor: 'rgba(75, 192, 192, 0.2)',
        borderColor: 'rgba(75, 192, 192, 1)',
        borderWidth: 1
      }]
    },
    options: {
      scales: {
        y: {
          beginAtZero: true
        }
      }
    }
  });
}
