const menus = [
  {id: '0', label: 'Introduction', path: '/', icon: 'fa fa-dashboard'},
  {
    id: '1', label: 'Fund Manager', path: '/', icon: 'fa fa-dashboard',
    submenu: [
      {
        id: 'fmInfo',
        icon: 'fa fa-microchip',
        label: 'Fund Manager Info',
        name: 'fundManagerInfo'
      },
      {
        id: 'transferPortfolio',
        icon: 'fa fa-microchip',
        label: 'Transfer Portfolio',
        name: 'transferPortfolio'
      }
    ]
  },
  {
    id: '2', label: 'Daliy Upload', path: '/', icon: 'fa fa-dashboard',
    submenu: [
      {
        id: 'uploadRates',
        icon: 'fa fa-microchip',
        label: 'Upload Rates',
        name: 'uploadRates'
      },
      {
        id: 'uploadPrices',
        icon: 'fa fa-microchip',
        label: 'Upload Prices',
        name: 'uploadPrices'
      },
      {
        id: 'getReports',
        icon: 'fa fa-microchip',
        label: 'Get Report',
        name: 'reports'
      }
    ]
  },
  // {
  //   id: '3', label: 'Manager Operator', path: '/', icon: 'fa fa-dashboard',
  //   submenu: [
  //     {
  //       id: 'portfolios',
  //       icon: 'fa fa-microchip',
  //       label: 'Portfolios',
  //       name: 'portfolios'
  //     },
  //     {
  //       id: 'positions',
  //       icon: 'fa fa-microchip',
  //       label: 'Positions',
  //       name: 'positions'
  //     }
  //   ]
  // }
]
export default menus
