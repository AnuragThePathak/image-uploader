import { ChangeEvent, FormEvent, useState } from "react"
import { upload } from "../server-requests"

export default function ImageUploader({ onUpload }: { onUpload: () => Promise<void> }) {
	const [selectedFile, setSelectedFile] = useState<File | null>(null)
	const [uploadedImage, setUploadedImage] = useState("")
	const [messageStatus, setMessageStatus] = useState(false)
	const [uploading, setUploading] = useState(false)

	function handleFileChange(e: ChangeEvent<HTMLInputElement>) {
		const files = e.target.files
		if (files !== null && files.length > 0) {
			const file = files[0]
			if (file.type === "image/jpeg" || file.type === "image/jpg" ||
				file.type === "image/png") {
				setSelectedFile(file)
			} else {
				alert("Only jpeg and png files are accepted")
				setSelectedFile(null)
			}
		}
	}

	async function handleUpload(e: FormEvent) {
		e.preventDefault()
		if (selectedFile === null) {
			alert("Please select a file to upload")
			return
		}
		const formData = new FormData()
		formData.append("file", selectedFile)
		setUploading(true)
		setUploadedImage(await upload(formData))
		await onUpload()
		setUploading(false)
		setMessageStatus(true)
	}

	return (
		<div className="flex flex-col space-y-2">
			{uploading ?
				<div className="loader border-t-4 border-b-4 border-blue-500 
				rounded-full w-16 h-16 spin self-center bg-gray-100 p-4" />
				: (
					<>
						<form onSubmit={handleUpload}
							className="flex flex-col p-4 space-y-8">
							<div className="flex flex-col space-y-2">
								<label className="text-lg">Select Image</label>
								<input type="file" id="file" accept="image/jpeg, image/png"
								onChange={handleFileChange} />
							</div>
							<div className="flex flex-row space-x-4 justify-center">
								<button type="submit"
									className="bg-blue-500 text-white hover:bg-white
					 hover:text-black border rounded-md py-1 px-3">
									Upload
								</button>
								<button type="reset"
									onClick={() => setSelectedFile(null)}
									className="bg-red-500 text-white hover:bg-white 
					 hover:text-black border rounded-md py-1 px-3">
									Clear
								</button>
							</div>
						</form>
						{messageStatus && <div className="max-w-sm w-full bg-white shadow-md rounded-lg p-4">
							<div className="flex items-center">
								<div className="bg-green-100 rounded-full p-2">
									<svg className="w-6 h-6 text-green-500"
										fill="none" stroke="currentColor"
										viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
										<path stroke-linecap="round"
											stroke-linejoin="round" stroke-width="2"
											d="M9 12l2 2l4-4m0-6a9 9 0 11-18 0 9 9 0 0118 0z"></path>
									</svg>
								</div>
								<div className="ml-3">
									<h3 className="text-green-700 font-semibold">
										Success</h3>
									<p className="text-green-600 text-sm">
										Image is uploaded successfully!</p>
								</div>
							</div>
						</div>}
						{uploadedImage && <div>
							<img src={uploadedImage} alt="Uploaded Image"
								className="max-h-80 mx-auto" />
						</div>}
					</>)
			}
		</div>
	)
}