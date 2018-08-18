const menus = [
  {id: 'managerMenu0', label: 'Introduction', path: '/', icon: 'fa fa-dashboard'},
  {
    id: 'managerMenu', label: 'Manager Operator', path: '/', icon: 'fa fa-dashboard',
    submenu: [
      {
        id: 'portfolios',
        icon: 'fa fa-microchip',
        label: 'Portfolios',
        name: 'portfolios'
      },
      {
        id: 'positions',
        icon: 'fa fa-microchip',
        label: 'Positions',
        name: 'positions'
      }
    ]
  }
  ]
  export default menus
  