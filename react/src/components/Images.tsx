import { useEffect, useState } from "react"
import { getImages } from "../server-requests"
import ImageUploader from "./ImageUploader"
import UploadedImages from "./UploadedImages"

export default function Images() {
	const [images, setImages] = useState<string[]>([])
	const [loading, setLoading] = useState<boolean>(false)

	async function fetchData() {
		setLoading(true)
		setImages(await getImages())
		setLoading(false)
	}

	useEffect(() => {
		fetchData()
	}, [])

	return (
		<div className="space-y-4">
			<ImageUploader onUpload={() => fetchData()} />
			{loading ? (
				<div>Loading...</div>
			) : (
				<UploadedImages images={images} />
			)}
		</div>
	)
}