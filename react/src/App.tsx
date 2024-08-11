import './App.css'
import Images from "./components/Images"

function App() {

  return (
    <main className="main">
      <div className="w-1/2 lg:w-1/3 border shadow p-6 mx-auto rounded-md flex flex-col
      bg-gray-100 space-y-6">
        <h1 className="text-3xl">Image Uploader</h1>
        <Images />
      </div>
    </main>
  )
}

export default App
